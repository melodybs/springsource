package springsource.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class ErrorPageController {
	
	@RequestMapping(value = "/public/error/403", method = RequestMethod.GET)
	public String accessDenied() {
		
		return "/public/error/403";
	}
	
	@RequestMapping(value = "/public/error/404", method = RequestMethod.GET)
	public String notFound() {
		
		return "/public/error/404";
	}
}
