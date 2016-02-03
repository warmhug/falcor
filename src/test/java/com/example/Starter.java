package com.example;

import io.netty.buffer.ByteBuf;
import io.netty.handler.logging.LogLevel;
import io.reactivex.netty.protocol.http.server.HttpServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import rx.Observable;

import static com.example.FalcorService.requestHandler;

@EnableAutoConfiguration
@SpringBootApplication
public class Starter extends AbstractServerExample {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(Starter.class, args);

        // HttpServer<ByteBuf, ByteBuf> server = RxNetty.createHttpServer(8900, requestHandler);
        // server.start();
        startRxNettyServer(args);
    }

    public static void startRxNettyServer(String[] args) {
        HttpServer<ByteBuf, ByteBuf> server;
        server = HttpServer.newServer(8900)
                .enableWireLogging(LogLevel.DEBUG)
                .start((req, resp) -> {
                            System.out.println("===== start handle request =====");
                            Observable observable = requestHandler(req, resp);
                            return observable;
                            // return resp.writeString(just("Hello World!"));
                        }
                );
        // setServerPort(server.getServerPort());
        if (shouldWaitForShutdown(args)) {
            server.awaitShutdown();
        }
    }
}
