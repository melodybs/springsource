package springsource.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.core.env.Environment;
import org.springframework.format.FormatterRegistry;
import org.springframework.format.datetime.DateFormatter;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.web.servlet.view.InternalResourceViewResolver;
import org.springframework.web.servlet.view.JstlView;
import org.springframework.web.servlet.view.UrlBasedViewResolver;
import org.springframework.web.servlet.view.tiles3.TilesConfigurer;
import org.springframework.web.servlet.view.tiles3.TilesView;


/**
 * {@link WebMvcConfigurerAdapter}: {@link WebMvcConfigurer}인터페이스. 모든 메소드를 구현하지 않은 추상 클래스.
 * {@link WebMvcConfigurationSupport}: @EnableWebMvc 사용하지 않아야 함.  이 클래스의 기본 설정 이외의 다른 설정을 추가 할 수도 있다.
 */
@Configuration
@EnableWebMvc
@ComponentScan(basePackages={ "springsource.web" })
//@ImportResource("classpath:/spring/spring-security.xml")
public class WebMvcContextConfiguration extends WebMvcConfigurerAdapter {
	
	//Logger logger = LoggerFactory.getLogger(WebMvcContextConfiguration.class);
	
/* Config */
	
	
/* Add */
	// resource(classpath)/META-INF/web-resources의 폴더및 파일을 resource폴더에 매핑
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		
		registry.addResourceHandler("/resources/**/*")
				.addResourceLocations("classpath:/META-INF/web-resources/");
	}
	
	//Interceptor
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		
		registry.addInterceptor(localeChangeInterceptor());
	}
	
	//Formatter
	@Override
	public void addFormatters(FormatterRegistry registry) {
	
		registry.addFormatter(new DateFormatter("yyyy-MM-dd HH:NN:SS"));
	}
	
	//Session
	/*@Override
	public void addArgumentResolvers(
			List<HandlerMethodArgumentResolver> argumentResolvers) {
		
		argumentResolvers.add(sessionAttributeProcessor());
	}*/
	
	//Session
	/*@Override
	public void addReturnValueHandlers(
			List<HandlerMethodReturnValueHandler> returnValueHandlers) {
		
		returnValueHandlers.add(sessionAttributeProcessor());
	}*/
	
/* Bean */
	//Tiles
	@Bean
	public TilesConfigurer tilesConfigurer() {
		
		TilesConfigurer tilesConfigurer = new TilesConfigurer();
		
		tilesConfigurer.setDefinitions(
				new String[] { "/WEB-INF/tiles-configuration.xml" });
		
		return tilesConfigurer;
	}
	@Bean
	public ViewResolver tilesViewResolver() {
		
		UrlBasedViewResolver urlBasedViewResolver = new UrlBasedViewResolver();
		
		urlBasedViewResolver.setOrder(0);
		urlBasedViewResolver.setViewClass(TilesView.class);
		
		return urlBasedViewResolver;
	}
	
	//ViewResolver
	@Bean
	public ViewResolver viewResolver() {
		
		InternalResourceViewResolver internalResourceViewResolver =
				new InternalResourceViewResolver();
		
		internalResourceViewResolver.setOrder(1);
		internalResourceViewResolver.setPrefix("/WEB-INF/views/");
		internalResourceViewResolver.setSuffix(".jsp");
		internalResourceViewResolver.setViewClass(JstlView.class);
		
		return internalResourceViewResolver;
	}
	
	//Locale
	@Bean
	public LocaleChangeInterceptor localeChangeInterceptor() {
		
		LocaleChangeInterceptor localeChangeInterceptor = 
				new LocaleChangeInterceptor();
		
		localeChangeInterceptor.setParamName("lang");
		
		return localeChangeInterceptor;
	}
	@Bean
	LocaleResolver localeResolver() {
		return new CookieLocaleResolver();
	}

	//MessageSource
	@Bean 
	public MessageSource messageSource() {
		
		ReloadableResourceBundleMessageSource messageSource =
				new ReloadableResourceBundleMessageSource();
		
		messageSource.setBasenames(new String[] { "classpath:/messages" });
		messageSource.setUseCodeAsDefaultMessage(true);
		
		return messageSource;
	}
	
	//Multipart Servlet 3.0
	@Bean
	public MultipartResolver multipartResolver() {
		
		return new StandardServletMultipartResolver();
	}
	
	//Session
	/*@Bean
	public SessionAttributeProcessor sessionAttributeProcessor() {
		
		return new SessionAttributeProcessor();
	}*/

	@Bean
	public PropertySourcesPlaceholderConfigurer 
			propertySourcesPlaceholderConfigurer() {
		
		return new PropertySourcesPlaceholderConfigurer();
	}
}
