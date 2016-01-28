package com.example;

import io.netty.buffer.ByteBuf;
import io.netty.handler.logging.LogLevel;
import io.reactivex.netty.protocol.http.server.HttpServer;

import static com.example.FalcorService.requestHandler;
import static rx.Observable.*;

public class RxNettyServer extends AbstractServerExample {

    public static void main(String[] args) {
        // HttpServer<ByteBuf, ByteBuf> server = RxNetty.createHttpServer(8900, requestHandler);
        // server.start();

//        startRxNettyServer(args);
    }

    public static void startRxNettyServer(String[] args) {
        HttpServer<ByteBuf, ByteBuf> server;
        server = HttpServer.newServer(8900)
                .enableWireLogging(LogLevel.DEBUG)
                .start((req, resp) -> {
                        System.out.println("===== start handle request =====");
                        requestHandler(req, resp);
                        return resp.writeString(just("Hello World!"));
                    }
                );
        // setServerPort(server.getServerPort());
        if (shouldWaitForShutdown(args)) {
            server.awaitShutdown();
        }
    }


}
