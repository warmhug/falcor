package com.example;

import com.netflix.falcor.protocol.http.server.FalcorRequestHandler;
import com.netflix.falcor.router.RequestContext;
import com.netflix.falcor.router.Route;
import com.netflix.falcor.util.RouteResult;
import io.netty.buffer.ByteBuf;
import io.netty.handler.logging.LogLevel;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import rx.Observable;
import static rx.Observable.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by hua on 16/1/27.
 */
public class RxNettyServerDemo  extends AbstractServerExample {

    public static void main(String[] args) {
        /*FalcorRequestHandler falcorRequestHandler = new FalcorRequestHandler(new Route<HttpServerRequest<Object>>() {
            @Override
            public Observable<RouteResult> call(RequestContext<HttpServerRequest<Object>> httpServerRequestRequestContext) {
                return null;
            }
        });

        HttpServer<ByteBuf, ByteBuf> server =  RxNetty.createHttpServer(8900, falcorRequestHandler);
        server.start();*/


        HttpServer<ByteBuf, ByteBuf> server;
        server = HttpServer.newServer()
                .enableWireLogging(LogLevel.DEBUG)
                .start((req, resp) -> {
                            requestHandler(req, resp);
                            return resp.writeString(just("Hello World!"));
                        }
                );
        if (shouldWaitForShutdown(args)) {
            server.awaitShutdown();
        }
        System.out.println("=======");
        System.out.println(server.getServerPort());
        setServerPort(server.getServerPort());
        //setServerPort(8900);

    }

    public static void requestHandler(HttpServerRequest<ByteBuf> req, HttpServerResponse<ByteBuf> resp) {
        FalcorRequestHandler handler = new FalcorRequestHandler(new Route<HttpServerRequest<ByteBuf>>() {

            @Override
            public Observable<RouteResult> call(RequestContext<HttpServerRequest<ByteBuf>> httpServerRequestRequestContext) {
                System.out.println("falcor test");
                return null;
            }
        });

        handler.handle(req, resp);
    }
}
