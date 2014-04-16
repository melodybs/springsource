package springsource.web.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import springsource.web.domain.SpringPosts;
import springsource.web.service.SpringPostsService;

import com.google.gson.Gson;

@Controller
public class MainPostAjax {
	
	@Autowired
	private SpringPostsService springPostsService;
	
	@RequestMapping(value = "/ajax/mainPost/{postName}", method = RequestMethod.GET)
	public ResponseEntity<String> mainTitle(@PathVariable String postName) {
		
		HttpHeaders responseHeaders = new HttpHeaders();
		Gson gson = new Gson();
		
		List<SpringPosts> postsList = this.springPostsService.findMainPosts();
		String json = gson.toJson(postsList);
		
		responseHeaders.add("Content-Type", "application/json; charset=utf-8");
		
		return new ResponseEntity<String>(json, responseHeaders, 
				HttpStatus.CREATED);
	}
}
