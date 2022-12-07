package generator;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.netty.http.HttpProtocol;
import reactor.netty.http.client.Http2AllocationStrategy;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

import java.time.Duration;

@Slf4j
@Configuration
public class HttpClientConfig {

    @Bean
    public HttpClient httpClient() {
        return HttpClient.create(ConnectionProvider.builder("custom")
                        .allocationStrategy(Http2AllocationStrategy.builder()
                                .maxConcurrentStreams(20)
                                .minConnections(8)
                                .maxConnections(500)
                                .build())
                        .evictInBackground(Duration.ofSeconds(60))
                        .maxLifeTime(Duration.ofSeconds(300))
                        .metrics(false)
                        .pendingAcquireMaxCount(500)
                        .build())
                .keepAlive(true)
                .protocol(HttpProtocol.H2C);
    }

}
