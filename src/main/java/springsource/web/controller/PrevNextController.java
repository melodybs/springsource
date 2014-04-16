package springsource.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PrevNextController {
	
	@RequestMapping(value = "/prevNext/{postName}/{postId}", 
			method = RequestMethod.GET) 
	public String prevPage(@PathVariable String postName,
			@PathVariable long postId, Model model) {
		
		return "redirect:/public/post/" + postName + "/" + postId;
	}
}
