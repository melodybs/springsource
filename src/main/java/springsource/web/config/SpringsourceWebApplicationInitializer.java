package springsource.web.config;

import java.util.EnumSet;

import javax.servlet.DispatcherType;
import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.security.config.BeanIds;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.request.RequestContextListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.DispatcherServlet;

public class SpringsourceWebApplicationInitializer implements
		WebApplicationInitializer {
	
	//Logger logger = LoggerFactory.getLogger(WebMvcContextConfiguration.class);

	private static final String DISPATCHER_SERVLET_NAME = "dispatcher";

	private static final Class<?>[] configurationClasses = new Class<?>[] { 
		WebMvcContextConfiguration.class 
	};

	// Multipart Servlet 3.0 Config
	private static final long MAX_FILE_UPLOAD_SIZE = 1024 * 1024 * 2; // 2Mb
	private static final int FILE_SIZE_THRESHOLD = 1024 * 1024; // 1Mb
	private static final long MAX_REQUEST_SIZE = -1L; // Nolimit

	@Override
	public void onStartup(ServletContext servletContext)
			throws ServletException {

		registerListener(servletContext);
		registerDispatcherServlet(servletContext);
		registerOpenEntityManagerInViewFilter(servletContext);
		registerSpringSecurityFilterChain(servletContext);
	}

	/* Method */ 
	/**
	 * {@link AnnotationConfigWebApplicationContext} 인스턴스를 만드는 Factory Mehtod.
	 */
	private AnnotationConfigWebApplicationContext createContext(
			final Class<?>... annotatedClasses) {

		AnnotationConfigWebApplicationContext context = new AnnotationConfigWebApplicationContext();

		context.register(annotatedClasses);

		return context;
	}

	/**
	 * {@link ContextLoaderListener}
	 */
	private void registerListener(ServletContext servletContext) {

		AnnotationConfigWebApplicationContext rootContext = createContext(configurationClasses);

		servletContext.addListener(new ContextLoaderListener(rootContext));
		// 지역화(우류 메시지 지역화)를 위한 리스너
		servletContext.addListener(new RequestContextListener());
	}

	/**
	 * {@link DispatcherServlet} 등록
	 */
	private void registerDispatcherServlet(ServletContext servletContext) {

		AnnotationConfigWebApplicationContext dispatcherContext = createContext(WebMvcContextConfiguration.class);

		ServletRegistration.Dynamic dispatcher = servletContext.addServlet(
				DISPATCHER_SERVLET_NAME, new DispatcherServlet(
						dispatcherContext));

		dispatcher.setLoadOnStartup(1);
		dispatcher.addMapping("/");
		// Multipart Servlet 3.0 Config
		dispatcher.setMultipartConfig(new MultipartConfigElement(null,
				MAX_FILE_UPLOAD_SIZE, MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD));
	}
	
	/**
	 * {@link OpenEntityManagerInViewFilter}
	 */
	private void registerOpenEntityManagerInViewFilter(
			ServletContext servletContext) {
		
		FilterRegistration.Dynamic registration = 
				servletContext.addFilter("openEntityManagerInView", 
						new OpenEntityManagerInViewFilter());
		
		registration.addMappingForUrlPatterns(
				EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), 
				false, "*.jsp");
		
		registration.addMappingForUrlPatterns(
				EnumSet.of(DispatcherType.REQUEST, DispatcherType.FORWARD), 
				false, "/j_spring_security_check");
	}

	/**
	 * 
	 * {@link DelegatingFilterProxy}
	 */
	private void registerSpringSecurityFilterChain(
			ServletContext servletContext) {
		
		FilterRegistration.Dynamic springSecurityFilterChain = 
				servletContext.addFilter(BeanIds.SPRING_SECURITY_FILTER_CHAIN, 
						new DelegatingFilterProxy());
		
		springSecurityFilterChain.addMappingForUrlPatterns(null, false, "/*");
	}
	/* // Method */
}
