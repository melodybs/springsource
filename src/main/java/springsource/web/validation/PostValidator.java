package springsource.web.validation;

import org.springframework.context.MessageSource;
import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import springsource.web.domain.SpringPosts;

/**
 * {@link SpringPosts}
 * {@link ValidationUtils}
 * {@link MessageSource}
 */
public class PostValidator implements Validator {
	
	@Override
	public boolean supports(Class<?> clazz) {
		
		return (SpringPosts.class).isAssignableFrom(clazz);
	}
	
	@Override
	public void validate(Object target, Errors errors) {
		
		ValidationUtils.rejectIfEmpty(errors, "main_title", "required", 
				new Object[] { "Main_title" });
		ValidationUtils.rejectIfEmpty(errors, "prev_title", "required", 
				new Object[] { "Prev_title" });
		ValidationUtils.rejectIfEmpty(errors, "title", "required", 
				new Object[] { "Title" });
	}
}
