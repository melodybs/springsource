package springsource.web.service;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.mvc.annotation.ResponseStatusExceptionResolver;

/**
 * Thrown when email or password are incorrect
 * @ResponseStatus: {@link ResponseStatusExceptionResolver}. 
 * 				               HTTP응답코드와 빈 ModelAndView 반환.
 *					           property: value, reason 
 */
@ResponseStatus(value = HttpStatus.FORBIDDEN)
public class AuthenticationException extends Exception {

	private String code;
	
	public AuthenticationException(String message, String code) {
		
		super(message);
		
		this.code = code;
	}
	
	public String getCode() {
		
		return this.code;
	}
}
