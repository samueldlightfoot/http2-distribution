package client;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

@Slf4j
@RestController
public class ClientController {
    private final WebClient webClient;

    public ClientController(HttpClient httpClient, WebClient.Builder builder) {
        this.webClient = builder
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .baseUrl("http://localhost:9995")
                .build();
    }

    @GetMapping("/callLoad")
    public Mono<Boolean> callLoad() {
        return webClient.post()
                .uri("callSuccess")
                .bodyValue(new Datas("somedata"))
                .accept(MediaType.APPLICATION_NDJSON)
                .retrieve()
                .bodyToMono(String.class)
                .map(r -> true)
                .doOnError(exc -> log.error("Error! {}", exc.getMessage()));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Datas {

        public String body;

    }

}
