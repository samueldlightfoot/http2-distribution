package client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.client.Http2AllocationStrategy;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.resources.LoopResources;

import java.time.Duration;

@Slf4j
@Configuration
public class HttpClientConfig {

    @Bean
    public HttpClient httpClient() {
        return HttpClient.create(ConnectionProvider.builder("custom")
                        .allocationStrategy(Http2AllocationStrategy.builder()
                                .maxConcurrentStreams(50)
                                .minConnections(4)
                                .maxConnections(99)
                                .build())
                        .evictInBackground(Duration.ofSeconds(60))
                        .maxLifeTime(Duration.ofSeconds(300))
                        .metrics(true)
                        .build())
                .keepAlive(true)
                .runOn(LoopResources.create("client", 16, true))
                .protocol(HttpProtocol.H2C);
    }

}
