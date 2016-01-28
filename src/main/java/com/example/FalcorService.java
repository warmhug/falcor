package com.example;

import com.netflix.falcor.model.FalcorPath;
import com.netflix.falcor.model.KeySegment;
import com.netflix.falcor.model.StringKey;
import com.netflix.falcor.protocol.http.client.FalcorHttpClient;
import com.netflix.falcor.protocol.http.server.FalcorRequestHandler;
import com.netflix.falcor.router.RequestContext;
import com.netflix.falcor.router.Route;
import com.netflix.falcor.util.RouteResult;
import io.netty.buffer.ByteBuf;
import io.netty.handler.logging.LogLevel;
import io.reactivex.netty.protocol.http.client.HttpClient;
import io.reactivex.netty.protocol.http.client.HttpClientRequest;
import io.reactivex.netty.protocol.http.client.HttpClientResponse;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import rx.Observable;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class FalcorService {
    protected static Logger logger = LoggerFactory.getLogger(FalcorService.class);

    public static void main(String[] args) {

         // createNettyGet("/hello");
    }

    public static void createNettyGet(String url) {
        HttpClient.newClient("127.0.0.1", 8900)
                .enableWireLogging(LogLevel.DEBUG)
                  /*Creates a GET request with URI "/hello"*/
                .createGet(url)
                  /*Prints the response headers*/
                .doOnNext(resp -> logger.info(resp.toString()))
                  /*Since, we are only interested in the content, now, convert the stream to the content stream*/
                .flatMap((HttpClientResponse<ByteBuf> resp) ->
                                resp.getContent()
                                     /*Convert ByteBuf to string for each content chunk*/
                                        .map(bb -> bb.toString(Charset.defaultCharset()))
                )
                  /*Block till the response comes to avoid JVM exit.*/
                .toBlocking()
                  /*Print each content chunk*/
                .forEach(logger::info);
    }

    public static void createClient(HttpServletRequest request, HttpServletResponse response) {
//        HttpClient<ByteBuf, ByteBuf> httpClient = HttpClient.newClient("127.0.0.1", 8900);

        createNettyGet(request.getRequestURI() + "?" + request.getQueryString());
        System.out.println(request.getRequestURL());
        System.out.println(request.getRequestURI());
        System.out.println(request.getQueryString());
        System.out.println(request.getParameter("paths"));

//        FalcorHttpClient falcorHttpClient = new FalcorHttpClient(httpClient);
//        StringKey stringKey1 = new StringKey("tttt1");
//        KeySegment[] keySegments = new KeySegment[]{stringKey1};
//        FalcorPath falcorPath = FalcorPath.of(keySegments);
//        FalcorPath[] falcorPaths = new FalcorPath[]{falcorPath};
//        List<FalcorPath> falcorPathArrayList = new ArrayList<FalcorPath>(Arrays.asList(falcorPaths));
//        falcorHttpClient.get(request.getRequestURI(), falcorPathArrayList);

//        return falcorHttpClient;
    }

    public static void requestHandler(HttpServerRequest<ByteBuf> req, HttpServerResponse<ByteBuf> resp) {
//        FalcorRequestHandler handler = new FalcorRequestHandler(new Route<HttpServerRequest<ByteBuf>>() {
//            @Override
//            public Observable<RouteResult> call(RequestContext<HttpServerRequest<ByteBuf>> httpServerRequestRequestContext) {
//                System.out.println("falcor test");
//                return null;
//            }
//        });

//        FalcorRequestHandler<ByteBuf,ByteBuf> handler = new FalcorRequestHandler<ByteBuf,ByteBuf>(new HttpServerRequest<ByteBuf>())

//        handler.handle(req, resp);
    }
}
