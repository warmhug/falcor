package com.example;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.netflix.falcor.model.*;
import com.netflix.falcor.protocol.http.client.FalcorHttpClient;
import com.netflix.falcor.protocol.http.server.FalcorRequestHandler;
import com.netflix.falcor.router.FalcorRequest;
import com.netflix.falcor.router.RequestContext;
import com.netflix.falcor.router.Route;
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
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.*;

public class FalcorService {
    protected static Logger logger = LoggerFactory.getLogger(FalcorService.class);

    private static String nettyHost = "127.0.0.1";
    private static int nettyPort = 8900;
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {
        testRx();

        String s = getResult(HttpClient.newClient(nettyHost, nettyPort).enableWireLogging(LogLevel.DEBUG).createGet("/hello"));
        System.out.println(s);
    }

    public static void testRx() {
        ArrayList<Number> list = new ArrayList<>();
        list.add(1);
        list.add(11);
        Observable.from(list).map(path -> {
            System.out.println(path);
            return "";
        });
        Observable.just("Hello, world!")
                .map(s -> {
                    System.out.println(s);
                    return s + " -Dan";
                }).subscribe(s -> System.out.println(s));
    }

    public static String getResult(HttpClientRequest<ByteBuf, ByteBuf> clientRequest) {
        StringBuilder result = new StringBuilder();
        // HttpClient.newClient(nettyHost, nettyPort).enableWireLogging(LogLevel.DEBUG).createGet(url)
        clientRequest.doOnNext(resp -> {
            /*Prints the response headers*/
            logger.info(resp.toString());
        })
        .flatMap((HttpClientResponse<ByteBuf> resp) ->
            /*convert the stream to the content stream*/
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

    public static String createNettyPost(String url, String body) {
        StringBuilder result = new StringBuilder();
        HttpClient.newClient(nettyHost, nettyPort)
                .enableWireLogging(LogLevel.DEBUG)
                .createPost(url);
        return result.toString();
    }

    public static String createNettyRequest(HttpServletRequest request, HttpServletResponse response) throws IOException {
        HttpClient<ByteBuf, ByteBuf> httpClient = HttpClient.newClient(nettyHost, nettyPort);
        FalcorHttpClient falcorHttpClient = new FalcorHttpClient(httpClient);
        String uri = request.getRequestURI();

        List<String> paths;
        switch (request.getMethod()) {
            case "GET":
                paths = new ArrayList<String>() {{add(request.getParameter("paths"));}};
                return getResult(falcorHttpClient.get(uri, getPaths(paths)));
            case "POST":
                String method = request.getParameter("method");
                if (method.equals("set")) {
                    JSONObject json = JSON.parseObject(request.getParameter("jsonGraph"));
                    if (json != null) {
                        paths = new ArrayList<String>() {{add(json.getString("paths"));}};
                        return getResult(falcorHttpClient.set(uri, getPaths(paths), json.getString("jsonGraph")));
                    }
                } else if (method.equals("call")) {
                    paths = new ArrayList<String>() {{add("[" + request.getParameter("callPath") + "]");}};
                    List<Object> args = new ArrayList<Object>() {{add(request.getParameter("arguments"));}};
                    return getResult(falcorHttpClient.call(uri, getPaths(paths), args));
                }

                // return createNettyPost(url, "");
            default:
                return "";
        }

//        System.out.println(request.getRequestURL());
//        System.out.println(request.getQueryString());
//        System.out.println(request.getParameter("paths"));

    }

    public static List<FalcorPath> getPaths(List<String> paths) throws IOException {
        if (paths != null) {
            return Arrays.asList(mapper.readValue(paths.get(0), FalcorPath[].class));
        }
        return Collections.emptyList();
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
//                List<FalcorPath> falcorPath = null;
//                try {
//                     falcorPath = getPaths(req.getQueryParameters().get("paths"));
//                     return httpServerRequestRequestContext.complete(FalcorPath.of(falcorPath.get(0).get(0)), falcorPath.get(0), new Atom("success!"));
//                } catch (IOException e) {
//                    e.printStackTrace();
//                    return null;
//                }

                FalcorPath matched = httpServerRequestRequestContext.getMatched();
                FalcorPath unmatched = httpServerRequestRequestContext.getUnmatched();
                FalcorValue val;
                if (unmatched.get(1).toString().equals("done")) {
                    matched = FalcorPath.of(new KeySegment[]{unmatched.get(0), unmatched.get(1)});
                    unmatched = FalcorPath.of(new KeySegment[]{new StringKey("name")});
                    val = new Ref(unmatched);
                } else {
                    matched = FalcorPath.of(new KeySegment[]{unmatched.get(0), unmatched.get(1)});
                    val = new Atom("success!");
                }
                return httpServerRequestRequestContext.complete(matched, unmatched, val);

                //return httpServerRequestRequestContext.atom("hiill");
            }
        });

        // handler.handle(req, resp).subscribe();
        return handler.handle(req, resp);
    }
}
