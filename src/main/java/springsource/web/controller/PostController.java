package springsource.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import springsource.web.domain.SpringPosts;
import springsource.web.service.SpringPostsService;

@Controller
public class PostController {
	
	@Autowired
	private SpringPostsService springPostsService;
	
	@RequestMapping(value = "/public/post/{postName}/{postId}",
			method = RequestMethod.GET)
	public String posts(@PathVariable("postName") String postName, 
			@PathVariable long postId, Model model) {
		
		SpringPosts post = this.springPostsService.findSpringPostById(postId);
		
		model.addAttribute("post", post);
		
		return "public/post/" + postName;
	}
}
