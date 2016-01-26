package com.example;

import com.netflix.falcor.model.FalcorMethod;
import com.netflix.falcor.model.FalcorPath;
import com.netflix.falcor.router.FalcorRequest;
import io.reactivex.netty.protocol.http.server.HttpServerRequest;
import io.reactivex.netty.protocol.http.server.HttpServerResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@Controller

public class IndexController {
	@RequestMapping("/")
	String home() {
		return "index";
	}

	@RequestMapping("/model.json")
	String model(HttpServletRequest request, HttpServletResponse response) {
//		FalcorMethod method = FalcorMethod.GET;
//		List<FalcorPath> paths = new ArrayList<FalcorPath>();
//		FalcorRequest falcorRequest = new FalcorRequest(method, paths);

		HttpServerRequest falcorRequest = new HttpServerRequest(null, null);
//		HttpServerResponse falcorResponse = new HttpServerResponse(null, null);


		return "xx";
	}

}
