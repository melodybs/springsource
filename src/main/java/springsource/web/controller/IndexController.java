package springsource.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import springsource.web.domain.SpringPosts;
import springsource.web.service.SpringPostsService;

@Controller
public class IndexController {
	
	@Autowired
	private SpringPostsService springPostsService;
	
	@RequestMapping(value = "/main")
	public ModelAndView indexPage() {
		
		ModelAndView mav = new ModelAndView("index");
		
		List<SpringPosts> springPostsList = 
				this.springPostsService.findAllPosts();
		
		mav.addObject("springPostsList", springPostsList);
	
		return mav;
	}
}
