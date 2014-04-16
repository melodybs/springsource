package springsource.web.method.support;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.PARAMETER, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SessionAttribute {
	
	/**
	 * 세션 속성에 바인드할 이름.
	 */
	String value() default "";
	
	/**
	 * 파라미터가 필수인지 여부. 
	 */
	boolean required() default true;
	
	/**
	 *  모델속성에 노출이 필요한지 여부
	 */
	boolean exposeAsModelAttribute() default false;
}
