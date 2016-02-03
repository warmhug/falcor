package com.example;

import com.netflix.falcor.protocol.http.client.FalcorHttpClient;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
public class IndexController {
	@RequestMapping("/")
	String home() {
		return "index";
	}

	@RequestMapping("/model.json")
	@ResponseBody
	String model(HttpServletRequest request, HttpServletResponse response) {
		String result = null;
		try {
			result = FalcorService.createNettyRequest(request, response);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return result;
		// return "{jsonGraph: y}";
	}

}
