package springsource.web.controller;

//import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.forwardedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.model;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.flash;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
//import org.springframework.test.web.server.MvcResult;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import springsource.web.config.TestDataContextConfiguration;
import springsource.web.config.TestPersistenceConfiguration;
import springsource.web.controller.ControllerTest.ControllerTestConfiguration;
import springsource.web.service.SpringPostsService;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
//import static org.springframework.test.web.servlet.setup.MockMvcBuilders.webAppContextSetup;
//import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
//import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.asyncDispatch;
//import static org.hamcrest.Matchers.hasItem;
//import static org.hamcrest.Matchers.hasProperty;
//import static org.hamcrest.Matchers.is;
//import static org.hamcrest.Matchers.allOf;

/**
 * https://github.com/spring-projects/spring-test-mvc
 * https://github.com/spring-projects/spring-mvc-showcase
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { 
		ControllerTestConfiguration.class,
		TestPersistenceConfiguration.class, 
		TestDataContextConfiguration.class })
public class ControllerTest {	
	
	@Autowired
	private IndexController indexController;
	@Autowired
	private AuthenticationController authenticationController;
	@Autowired
	private ContentOnlyTestController contentOnlyTestController;
	@Autowired
	private WriteController writeController;
	@Autowired
	private PrevNextController prevNextController;
	@Autowired
	private PostController postController;
	
	@Autowired
	private SpringPostsService springPostsService;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		
		this.mockMvc = MockMvcBuilders.standaloneSetup(
				indexController, 
				authenticationController,
				contentOnlyTestController,
				writeController,
				prevNextController,
				postController).build();
		//.alwaysExpect(status().isMovedTemporarily())
		//.alwaysExpect(status().isOk())
	}
	
	@Test
	public void indexPage() throws Exception {

		
		this.mockMvc.perform(get("/main"))
	            //.param("key", "value")
	            .andExpect(status().isOk())
	            .andExpect(forwardedUrl("index"));
	            //.andExpect(view().name(containsString("index")))
				//.andExpect(model().attribute("foo", "bar"))
				//.andExpect(model().size(1));
				//.andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
				//.andExpect(content().string("Hello world!"))
				//.andExpect(redirectedUrl("/redirect/a123?date=12%2F31%2F11"));
	            //.andExpect(model().attributeExists("page_error"));
	}
	
	@Test
	public void loginPage() throws Exception {
		
		this.mockMvc.perform(get("/public/authentication/login"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("public/authentication/login"));
	}	
	
	@Test
	public void contentOnlyPage() throws Exception {
		
		this.mockMvc.perform(get("/secured/contentOnlyTest"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("secured/contentOnlyTest"));
	}
	
	@Test
	public void writePage() throws Exception {
		
		this.mockMvc.perform(get("/secured/admin/write"))
				.andExpect(status().isOk())
				.andExpect(forwardedUrl("secured/admin/write"));
	}
	
	@Test
	public void prevNextPage() throws Exception {
		
		this.mockMvc.perform(get("/prevNext/spring/1"))
				.andExpect(status().isFound())
				.andExpect(redirectedUrl("public/post/spring/1"));
	}
	
	@Test
	public void postPage() throws Exception {
		
		this.mockMvc.perform(get("/public/post/spring/1"))
				.andExpect(status().isOk())
				.andExpect(model().size(1))
				.andExpect(forwardedUrl("public/post/spring"));
	}
	
/*	@After
	public void verify() {
		
	}*/
	
    @Configuration
    static class ControllerTestConfiguration {
    	
        @Bean
        public IndexController indexController() {
        	
            return new IndexController();
        }
        
        @Bean 
        public AuthenticationController authenticationController() {
        	
        	return new  AuthenticationController();
        }
        
        @Bean 
        public ContentOnlyTestController contentOnlyTestController() {
        	
        	return new  ContentOnlyTestController();
        }
        
        @Bean
        public WriteController writeController() {
        	
        	return new  WriteController();
        }
        
        @Bean
        public PrevNextController prevNextController() {
        	
        	return new PrevNextController();
        }
        
        @Bean
        public PostController postController() {
        	
        	return new PostController();
        }
        
        /*@Bean
        public SpringPostsService springPostsService() {
        	
        	return Mockito.mock(SpringPostsService.class);
        }*/
    }
}
