package springsource.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ContentOnlyTestController {
	
	@RequestMapping(value = "secured/contentOnlyTest")
	public ModelAndView ContentOnlyTestPage() {
		
		ModelAndView mav = new ModelAndView("secured/contentOnlyTest");
		
		return mav;
	}
}