package springsource.web.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import springsource.web.config.TestDataContextConfiguration;
import springsource.web.config.TestPersistenceConfiguration;
import springsource.web.controller.AjaxTest.AjaxTestConfiguration;
import springsource.web.service.SpringPostsService;

import com.google.gson.Gson;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { 
		AjaxTestConfiguration.class,
		TestPersistenceConfiguration.class, 
		TestDataContextConfiguration.class })
public class AjaxTest {

	@Autowired
	private PrevNextPostAjax prevNextPostAjax;
	@Autowired
	private PossiblePrevPostAjax possiblePrevPostAjax;
	@Autowired
	private MainPostAjax mainPostAjax;
	
	@Autowired
	private SpringPostsService springPostsService;
	
	private MockMvc mockMvc;
	
	@Before
	public void setup() {
		
		this.mockMvc = MockMvcBuilders.standaloneSetup(
				prevNextPostAjax,
				possiblePrevPostAjax,
				mainPostAjax).build();
	}
	
	@Test
	public void prevNextPostAjax() throws Exception {

		this.mockMvc.perform(get("/ajax/prevNextPost/spring")
				.param("postId", "1"))
				.andExpect(status().isCreated())
				.andExpect(content()
						.contentType("application/json; charset=utf-8"));
	}
	
	@Test 
	public void possiblePrevPostAjax() throws Exception {
		
		this.mockMvc.perform(get("/ajax/possiblePrevPost/spring")
				.param("id_main", "0"))
				.andExpect(status().isCreated())
				.andExpect(content()
						.contentType("application/json; charset=utf-8"));
	}
	
	@Test 
	public void mainPostAjax() throws Exception {
		
		this.mockMvc.perform(get("/ajax/mainPost/spring"))
				.andExpect(status().isCreated())
				.andExpect(content()
						.contentType("application/json; charset=utf-8"));
	}
	
	@Configuration
	static class AjaxTestConfiguration {
		
		@Bean
		public PrevNextPostAjax prevNextPostAjax() {
			
			return new PrevNextPostAjax();
		}
		
		@Bean
		public PossiblePrevPostAjax possiblePrevPostAjax() {
			
			return new PossiblePrevPostAjax();
		}
		
		@Bean MainPostAjax mainPostAjax() {
			
			return new MainPostAjax();
		}
		
        /*@Bean
        public SpringPostsService springPostsService() {
        	
        	return Mockito.mock(SpringPostsService.class);
        }*/
	}
}


