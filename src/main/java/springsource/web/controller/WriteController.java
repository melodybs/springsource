package springsource.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

import springsource.web.domain.SpringPosts;
import springsource.web.service.SpringPostsService;

@Controller
public class WriteController {
	
	@Autowired
	private SpringPostsService springPostsService;
	
	@RequestMapping(value = "/secured/admin/write", method = RequestMethod.GET)
	public ModelAndView writePage() {
		
		ModelAndView mav = new ModelAndView("secured/admin/write");
		
		return mav;
	}
	
	@RequestMapping(value = "/secured/admin/modify/{postId}", 
			method = RequestMethod.GET)
	public ModelAndView modifyPage(@PathVariable long postId) {
		
		SpringPosts post = this.springPostsService.findSpringPostById(postId);
		
		ModelAndView mav = new ModelAndView("secured/admin/write");
	
		mav.addObject("post", post);
		
		return mav;
	}

	/*@RequestMapping(value = "/secured/admin/spring/write", 
			method = RequestMethod.POST)
	public String write(@PathVariable String postName, 
			@PathVariable long postId) {
		
		return "redirect:public/post/" + postName + "/" + postId;
	}
	
	@RequestMapping(value = "/secured/admin/spring/save", method = RequestMethod.POST)
	public String save(@PathVariable String postName, 
			@PathVariable long postId) {
		
		return "redirect:secured/admin/write/" + postId;
	}*/
}
