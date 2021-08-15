package controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

@Controller
public class IndexController {

	@Autowired
	private RequestMappingHandlerMapping re;
	
	@GetMapping({ "/", "/index" })
	public String main() {
		return "index";
	}

	@GetMapping("/sitemap.xml")
	public void getSitemap(HttpServletResponse response) {
		response.setContentType("application/xml");
		Map<RequestMappingInfo, HandlerMethod> handlerMethods = re.getHandlerMethods();
		List<String> urls = new ArrayList<>();
		for (Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
			urls.addAll((entry.getKey().getPatternsCondition().getPatterns()));
		}
		
		// Construct XML response from urls and return it
		for(String x : urls) {
			try {
				if(!x.equals("/")) {
					System.out.println(x);
					response.getOutputStream().println(x);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			response.getOutputStream().flush();
			response.getOutputStream().close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
