package client;

import org.springframework.boot.web.embedded.netty.NettyReactiveWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import reactor.netty.http.HttpProtocol;

@Configuration
public class HttpServerConfig {

    @Component
    public static class NettyWebServerFactoryPortCustomizer
            implements WebServerFactoryCustomizer<NettyReactiveWebServerFactory> {

        @Override
        public void customize(NettyReactiveWebServerFactory serverFactory) {
            serverFactory.addServerCustomizers(server -> server.protocol(HttpProtocol.H2C));
        }
    }

}
