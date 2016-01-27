package com.example;

import com.netflix.falcor.protocol.http.server.FalcorRequestHandler;
import com.netflix.falcor.router.RequestContext;
import com.netflix.falcor.router.Route;
import com.netflix.falcor.util.RouteResult;
import io.netty.buffer.ByteBuf;
import io.reactivex.netty.RxNetty;
import io.reactivex.netty.protocol.http.server.HttpServer;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import rx.Observable;

import java.util.Map;

public final class HelloWorldServer {

    static final int DEFAULT_PORT = 8090;

//    private final int port;

//    public HelloWorldServer(int port) {
//        this.port = port;
//    }
//
//    public HttpServer<ByteBuf, ByteBuf> createServer() {
//        return RxNetty.createHttpServer(port, (request, response) -> {
//            printRequestHeader(request);
//            requestHandler(request);
//            return response.writeStringAndFlush("Hello World!");
//        });
//    }
//
//    public void printRequestHeader(HttpServerRequest<ByteBuf> request) {
//        System.out.println("New request received");
//        System.out.println(request.getHttpMethod() + " " + request.getUri() + ' ' + request.getHttpVersion());
//        for (Map.Entry<String, String> header : request.getHeaders().entries()) {
//            System.out.println(header.getKey() + ": " + header.getValue());
//        }
//    }
//
//    public static void main(final String[] args) {
//        System.out.println("HTTP hello world server starting ...");
//        new HelloWorldServer(DEFAULT_PORT).createServer().startAndWait();
//    }
//
//    public void requestHandler(HttpServerRequest request) {
//        FalcorRequestHandler handler = new FalcorRequestHandler(new Route<HttpServerRequest>() {
//            @Override
//            public Observable<RouteResult> call(RequestContext<HttpServerRequest> httpServerRequestRequestContext) {
//                return null;
//            }
//        });
//
//    }
}