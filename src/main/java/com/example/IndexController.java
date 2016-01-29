package com.example;

import com.netflix.falcor.protocol.http.client.FalcorHttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class IndexController {
	@RequestMapping("/")
	String home() {
		return "index";
	}

	@RequestMapping("/model.json")
	@ResponseBody
	String model(HttpServletRequest request, HttpServletResponse response) {

//		FalcorHttpClient falcorHttpClient = FalcorService.createClient(request, response);
		String result = FalcorService.createClient(request, response);
		System.out.println(result);
		return result;

//		return "{jsonGraph: y}";
	}

}
