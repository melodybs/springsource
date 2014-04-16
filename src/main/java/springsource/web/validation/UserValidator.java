package springsource.web.validation;

import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import springsource.web.domain.User;


/**
 * {@link User}
 * {@link ValidationUtils}
 * {@link MessageSource}
 */
public class UserValidator implements Validator {
	
	private static final String EMAIL_PATTERN = 
			"^[_A-Za-z0-9-]+(\\.[_A-Za-z0-9-]+)*@" + 
			"[A-Za-z0-9]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";
	private static final String PASSWORD_PATTERN = 
			"((?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%]).{6,20})";
	private static final String NAME_PATTERN = 
			"^[가-힣]{2,4}|[a-zA-Z]{2,10}\\s[a-zA-Z]{2,10}$";
	//private static final String USERNAME_PATTERN = "^[a-z0-9_-]{3,15}$";
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return (User.class).isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmpty(
				errors, "email", "required", new Object[] { "Email" });
		ValidationUtils.rejectIfEmpty(
				errors, "password", "required", new Object[] { "Password" });
		ValidationUtils.rejectIfEmpty(
				errors, "name", "required", new Object[] { "Name" });
		
		
		//email pattern
		if (!errors.hasFieldErrors("email")) {
			
			User user = (User) target;
			String email = user.getEmail();
			
			if (!email.matches(EMAIL_PATTERN)) {
				
				errors.rejectValue("email", "invalid");
			}
		}
		
		//password pattern
		if (!errors.hasFieldErrors("password")) {
			
			User user = (User) target;
			String email = user.getPassword();
			
			if (!email.matches(PASSWORD_PATTERN)) {
				
				errors.rejectValue("password", "invalid");
			}
		}
		

		//name pattern
		if (!errors.hasFieldErrors("name")) {
			
			User user = (User) target;
			String email = user.getName();
			
			if (!email.matches(NAME_PATTERN)) {
				
				errors.rejectValue("password", "invalid");
			}
		}
	}
}
