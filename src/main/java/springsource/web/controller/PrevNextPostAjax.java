package springsource.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import springsource.web.domain.SpringPosts;
import springsource.web.service.SpringPostsService;

import com.google.gson.Gson;

@Controller
public class PrevNextPostAjax {
	
	@Autowired
	private SpringPostsService springPostsService;
	
	@RequestMapping(value = "/ajax/prevNextPost/{postName}", 
			method = RequestMethod.GET)
	public  @ResponseBody ResponseEntity<String> prevPost(
			@PathVariable String postName, @RequestParam long postId) {
		
		HttpHeaders responseHeaders = new HttpHeaders();
		Gson gson = new Gson();
		
		SpringPosts post = this.springPostsService.findSpringPostById(postId);
		String json = gson.toJson(post);
		
		/*String json = 
				this.springPostsService.findPostAsJsonString(postName, postId);*/
		
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		
		return new ResponseEntity<String>(json, responseHeaders, 
				HttpStatus.CREATED); 
	}
}