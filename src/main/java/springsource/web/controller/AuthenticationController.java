package springsource.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContext;
import org.springframework.webflow.action.EventFactorySupport;
import org.springframework.webflow.execution.Event;
import org.springframework.webflow.mvc.servlet.MvcExternalContext;

import springsource.web.domain.User;
import springsource.web.service.AuthenticationException;
import springsource.web.service.UserService;

@Controller
public class AuthenticationController {
	
	Logger log = LoggerFactory.getLogger(AuthenticationController.class);
	
	//public static final String AUTHENTICATED_USER_KEY="authenticatedUser";
	//public static final String LOGIN_FAILED_KEY = "public.authentication.login.failed";
	
	@Autowired
	private UserService userService;
	
	/*public AuthenticationForm initializeForm() {
		
		return new AuthenticationForm();
	}*/
	
	@RequestMapping(value = "/public/authentication/login", 
			method = RequestMethod.GET)
	public ModelAndView LoginPage() {
		
		ModelAndView mav = new ModelAndView("public/authentication/login");
		//mav.addObject("authenticationForm", initializeForm());
		
		User user = this.userService.findUserById(1L);
		mav.addObject("user", user);
		
		return mav;
	}
	
	//spring-security
	/*@RequestMapping(value ="/public/authentication/authenticate",
			method = RequestMethod.POST)
	public ModelAndView previous(
			@ModelAttribute AuthenticationForm authenticationForm,
			Errors errors,
			ModelAndView mav,
			HttpSession httpSession,
			HttpServletRequest httpServletRequest) {
	
		 log.info(authenticationForm.getEmail(), authenticationForm.getPassword());
		
		try {
			
			authenticate(authenticationForm, httpSession);
			
			mav.addObject("authenticationOk", "true");
			mav.addObject("email", authenticationForm.getEmail());
			mav.setViewName("main");
			
		} catch (AuthenticationException e) {
			
			errors.reject(LOGIN_FAILED_KEY, 
					new Object[] { new RequestContext(httpServletRequest)
							.getMessage(e.getCode()) }, null);
			
			mav.setViewName("/public/authentication/login");
		}
		
		return mav;
	}*/
	
	/*@RequestMapping(value ="/public/authentication/authenticate",
			params = "_eventId_previous", method = RequestMethod.POST)
	public ModelAndView previous(ModelAndView mav) {
	
		mav.setViewName("main");
		
		return mav;
	}*/
	
/* POJO Logic */
	/*public Event authenticate(AuthenticationForm authenticationForm,
			MvcExternalContext externalContext, MessageContext messageContext) {
		
		try {
			
			authenticate(authenticationForm, ((HttpServletRequest) 
					externalContext.getNativeRequest()).getSession());
			
		} catch (AuthenticationException authenticationException) {
			
			messageContext.addMessage(
					new MessageBuilder().error().code(LOGIN_FAILED_KEY)
							.arg(authenticationException.getLocalizedMessage()).build());
			
			return new EventFactorySupport().error(this);
		}
		
		return new EventFactorySupport().success(this);
	}*/
	
/* Helpers */
	/*public void authenticate(AuthenticationForm authenticationForm, 
			HttpSession httpSession) throws AuthenticationException {
		
		User user = userService.login(authenticationForm.getEmail(), 
				authenticationForm.getPassword());
		
		httpSession.setAttribute(AUTHENTICATED_USER_KEY, user);
	}*/
}
