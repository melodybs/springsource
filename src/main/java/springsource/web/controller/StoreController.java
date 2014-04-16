package springsource.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import springsource.web.service.SpringPostsService;

@Component
public class StoreController {
	
	@Autowired
	private SpringPostsService springPostsService;
	
/* Mehtod */
	public StoreForm initializeForm() {
		
		StoreForm storeForm = new StoreForm();
		
		return storeForm;
	}
}
