package generator;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Slf4j
@RestController
public class LoadController {
    private final WebClient webClient;

    public LoadController(HttpClient httpClient, WebClient.Builder builder) {
        this.webClient = builder
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("http://localhost:9991")
                .build();
    }

    @GetMapping("/call/{count}/{concurrency}")
    public Mono<Boolean> callService(@PathVariable int count, @PathVariable int concurrency) {
        return Flux.range(0, count)
                .flatMap(i -> webClient.get()
                        .uri("callLoad")
                        .retrieve()
                        .bodyToMono(Boolean.class), concurrency)
                .doOnError(exc -> log.error("Error! {}", exc.getMessage()))
                .then(Mono.just(true));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Datas {

        public String body;

    }

}
