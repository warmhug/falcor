package com.example;

import com.netflix.falcor.model.FalcorPath;
import com.netflix.falcor.model.KeySegment;
import com.netflix.falcor.model.StringKey;
import io.netty.buffer.ByteBuf;
import io.netty.handler.logging.LogLevel;
import io.reactivex.netty.protocol.http.server.HttpServer;
import org.springframework.boot.logging.ClasspathLoggingApplicationListener;
import rx.Observable;

import java.util.*;

import static com.example.FalcorService.requestHandler;
import static rx.Observable.*;

public class RxNettyServer extends AbstractServerExample {

    public static void main(String[] args) {
        // HttpServer<ByteBuf, ByteBuf> server = RxNetty.createHttpServer(8900, requestHandler);
        // server.start();

//        startRxNettyServer(args);

//        StringKey stringKey1 = new StringKey("tttt1");
//        KeySegment[] keySegments = new KeySegment[]{stringKey1};
//        FalcorPath falcorPath = FalcorPath.of(keySegments);
//        ArrayList<FalcorPath> list = new ArrayList<>();
//        list.add(falcorPath);
//        Observable.from(list).map(path -> {
//            System.out.println(path);
//            return "";
//        });
//        Observable.just("Hello, world!")
//                .map(s -> {
//                    System.out.println(s);
//                   return s + " -Dan";
//                }).subscribe(s -> System.out.println(s));
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
