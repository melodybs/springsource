package springsource.web.controller;

import javax.inject.Inject;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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

	@Inject
	private ConnectionRepository connectionRepository;
	
	//Social Login Redirect /signup
	@RequestMapping(value = "/signup", method = RequestMethod.GET)
	public String signup(Model model) {
		
		Connection<Facebook> connection =
				connectionRepository.findPrimaryConnection(Facebook.class);
		
		if (connection == null) {
			
			return "redirect:/public/authentication/login";
		}
		
		model.addAttribute("profile", 
				connection.getApi().userOperations().getUserProfile());
				
		return "/public/authentication/signup";
	}
}
