package springsource.web.controller;

import java.security.Principal;

import javax.inject.Inject;
import javax.inject.Provider;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import springsource.web.service.UserService;

@Controller
public class SocialController {
	
	private static Logger log = LoggerFactory.getLogger(SocialController.class);
	
	/*private final UserService userService;
	
	private final Provider<ConnectionRepository> connectionRepositoryProvider;
	
	@Inject
	public SocialController(
			Provider<ConnectionRepository> connectionRepositoryProvider,
			UserService userService) {
		
		this.connectionRepositoryProvider = connectionRepositoryProvider;
		this.userService = userService;
	}
	
	@RequestMapping("/")
	public String main(Principal currentUser, Model model) {
		
		model.addAttribute("connectionsToProviders", 
				getConnectionRepository().findAllConnections());
		model.addAttribute(userService.findUserByEmail(currentUser.getName()));
		
		return "/main";
	}
	
	private ConnectionRepository getConnectionRepository() {
		
		return connectionRepositoryProvider.get();
	}*/
	
	@RequestMapping(value = "/signin", method = RequestMethod.GET)
	public void signin() {
		
	}
}
