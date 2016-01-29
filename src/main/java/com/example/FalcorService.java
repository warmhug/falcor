package com.example;

import com.netflix.falcor.model.*;
import com.netflix.falcor.protocol.http.client.FalcorHttpClient;
import com.netflix.falcor.protocol.http.server.FalcorRequestHandler;
import com.netflix.falcor.router.FalcorRequest;
import com.netflix.falcor.router.RequestContext;
import com.netflix.falcor.router.Route;
import com.netflix.falcor.router.Route1;
import com.netflix.falcor.util.Complete;
import com.netflix.falcor.util.RouteResult;
import io.netty.buffer.ByteBuf;
import io.netty.handler.logging.LogLevel;
import io.reactivex.netty.protocol.http.client.HttpClient;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import io.reactivex.netty.protocol.http.server.HttpServerResponseImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;
import rx.Subscriber;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.*;

public class FalcorService {
    protected static Logger logger = LoggerFactory.getLogger(FalcorService.class);

    public static void main(String[] args) {

         // createNettyGet("/hello");
    }

    public static String createNettyGet(String url) {
        StringBuilder result = new StringBuilder();
        HttpClient.newClient("127.0.0.1", 8900)
                .enableWireLogging(LogLevel.DEBUG)
                  /*Creates a GET request with URI "/hello"*/
                .createGet(url)
                  /*Prints the response headers*/
                .doOnNext(resp -> {
                    logger.info("===== have response =====");
                    logger.info(resp.toString());
                })
                  /*Since, we are only interested in the content, now, convert the stream to the content stream*/
                .flatMap((HttpClientResponse<ByteBuf> resp) ->
                                resp.getContent()
                                     /*Convert ByteBuf to string for each content chunk*/
                                        .map(bb -> bb.toString(Charset.defaultCharset()))
                )
                  /*Block till the response comes to avoid JVM exit.*/
                .toBlocking()
                  /*Print each content chunk*/
                //.forEach(logger::info);
                .forEach(ct -> {
                    System.out.println("===== result =====");
                    logger.info(ct);
                    result.append(ct);
                });
        return result.toString();
    }

    public static String createClient(HttpServletRequest request, HttpServletResponse response) {

        return createNettyGet(request.getRequestURI() + "?" + request.getQueryString());

//        System.out.println(request.getRequestURL());
//        System.out.println(request.getRequestURI());
//        System.out.println(request.getQueryString());
//        System.out.println(request.getParameter("paths"));

//        HttpClient<ByteBuf, ByteBuf> httpClient = HttpClient.newClient("127.0.0.1", 8900);
//        FalcorHttpClient falcorHttpClient = new FalcorHttpClient(httpClient);

//        StringKey stringKey1 = new StringKey("tttt1");
//        KeySegment[] keySegments = new KeySegment[]{stringKey1};
//        FalcorPath falcorPath = FalcorPath.of(keySegments);
//        FalcorPath[] falcorPaths = new FalcorPath[]{falcorPath};
//        List<FalcorPath> falcorPathArrayList = new ArrayList<FalcorPath>(Arrays.asList(falcorPaths));
//        falcorHttpClient.get(request.getRequestURI(), falcorPathArrayList);

//        return falcorHttpClient;
    }

    public static Observable requestHandler(HttpServerRequest<ByteBuf> req, HttpServerResponse<ByteBuf> resp) {
        FalcorRequestHandler handler = new FalcorRequestHandler(new Route<HttpServerRequest<ByteBuf>>() {
            private int id;

            @Override
            public Route<HttpServerRequest<ByteBuf>> withRequest(FalcorRequest<HttpServerRequest<ByteBuf>> request) {
                System.out.println(request);
                return null;
            }

            @Override
            public Route<HttpServerRequest<ByteBuf>> withPaths(FalcorPath matched, FalcorPath unmatched) {
                System.out.println("debug");
                return null;
            }

            @Override
            public Observable<RouteResult> call(RequestContext<HttpServerRequest<ByteBuf>> httpServerRequestRequestContext) {
                StringKey stringKey1 = new StringKey("simple");
                KeySegment[] keySegments = new KeySegment[]{stringKey1};
                FalcorPath falcorPath = FalcorPath.of(keySegments);
                return httpServerRequestRequestContext.complete(falcorPath, falcorPath, new Atom("success!"));
                //return null;
                //return httpServerRequestRequestContext.atom("hiill");
            }
        });

        // handler.handle(req, resp).subscribe();
        return handler.handle(req, resp);
    }
}
