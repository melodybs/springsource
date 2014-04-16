package springsource.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;
import org.springframework.webflow.definition.registry.FlowDefinitionRegistry;
import org.springframework.webflow.executor.FlowExecutor;
import org.springframework.webflow.mvc.servlet.FlowHandlerAdapter;
import org.springframework.webflow.mvc.servlet.FlowHandlerMapping;

@Configuration
@ImportResource("classpath:/spring/webflow-config.xml")
public class WebflowContextConfiguration {
	
	@Autowired
	private FlowExecutor flowExecutor;
	@Autowired
	private FlowDefinitionRegistry flowRegistry;
	@Autowired
	private LocaleChangeInterceptor localeChangeInterceptor;
	
	@Bean
	public FlowHandlerAdapter flowHandlerAdapter() {
		FlowHandlerAdapter flowHandlerAdapter = new FlowHandlerAdapter();
		flowHandlerAdapter.setFlowExecutor(flowExecutor);
		
		return flowHandlerAdapter;
	}
	
	@Bean
	public FlowHandlerMapping flowHandlerMapping() {
		FlowHandlerMapping flowHandlerMapping = new FlowHandlerMapping();
		
		flowHandlerMapping.setInterceptors(
				new Object[] { localeChangeInterceptor });
		flowHandlerMapping.setFlowRegistry(flowRegistry);
		flowHandlerMapping.setOrder(0);
		
		return flowHandlerMapping;
	}
}
