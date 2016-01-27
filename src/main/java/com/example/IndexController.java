package com.example;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {
	@RequestMapping("/")
	String home() {
		return "index";
	}

	@RequestMapping("/model.json")
	String model(HttpServletRequest request, HttpServletResponse response) {
//		FalcorRequestHandler falcorRequestHandler = new FalcorRequestHandler(Route<HttpServerRequest>)


//		HttpClient nettyHttpClient = RxNetty.createHttpClient("127.0.0.1", 8900);
//		Observable<HttpClientResponse> responseObservable = nettyHttpClient.submit(HttpClientRequest.createGet("/"));
//
//		FalcorRequestHandler falcorRequestHandler = new FalcorRequestHandler(new Route<HttpServerRequest<Object>>() {
//			@Override
//			public Observable<RouteResult> call(RequestContext<HttpServerRequest<Object>> httpServerRequestRequestContext) {
//				return null;
//			}
//		});
//		RxNetty.createHttpServer(8900, falcorRequestHandler);


		return "xx";
	}

}
