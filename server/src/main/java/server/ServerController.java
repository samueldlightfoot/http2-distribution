package server;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.time.Duration;

@Slf4j
@RestController
public class ServerController {

    @PostMapping("/callSuccess")
    public Mono<Boolean> callServiceSuccess(@RequestBody Datas datas) {
        log.debug("Received call success");
        return Mono.just(true)
                .delayElement(Duration.ofMillis(150));
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class Datas {

        public String body;

    }

}
