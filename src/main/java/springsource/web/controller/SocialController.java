package springsource.web.controller;

import java.security.Principal;

import javax.inject.Inject;
import javax.inject.Provider;

import org.springframework.social.connect.ConnectionRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import springsource.web.service.UserService;

//@Controller
public class SocialController {
	
	/*private final Provider<ConnectionRepository> connectionRepositoryProvider;
	
	private final UserService userService;
	
	@Inject
	public HomeController(
			Provider<ConnectionRepository> connectionRepositoryProvider,
			UserService userService) {
		
		this.connectionRepositorProvider = connectionRepositoryProvider;
		this.userService = userService;
	}
	
	@RequestMapping("/")
	public String home(Principal currentUser, Model model) {
		
		model.addAttribute("connectionsToProviders", 
				getConnectionRepository().findAllConnections());
		model.addAttribute(userService.findUserByEmail(currentUser.getName()));
		
		return "home";
	}
	
	private ConnectionRepository getConnectionRepository() {
		
		return connectionRepositoryProvider.get();
	}*/
}
