package com.example;

import com.netflix.falcor.protocol.http.client.FalcorHttpClient;
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

//		FalcorHttpClient falcorHttpClient = FalcorService.createClient(request, response);
		FalcorService.createClient(request, response);

		return "xx";
	}

}
