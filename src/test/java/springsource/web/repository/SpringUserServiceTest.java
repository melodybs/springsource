package springsource.web.repository;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertSame;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import springsource.web.config.TestDataContextConfiguration;
import springsource.web.config.TestPersistenceConfiguration;
import springsource.web.domain.Permission;
import springsource.web.domain.Role;
import springsource.web.domain.User;
import springsource.web.service.AuthenticationException;
import springsource.web.service.UserService;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = { 
		TestPersistenceConfiguration.class, 
		TestDataContextConfiguration.class })
public class SpringUserServiceTest {
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private UserRepository userRepository;
	
	private User user;
	private User targetUser;
	
	@Before
	public void setup(){

		/*Mockito.when(userService.findUserByEmail("guest@gmail.com"))
				.thenReturn(userService.findUserById(1));*/
	}

/* 아이디, 이메일로 유저 찾기 */
	@Test
	public void testFindUser() {
		
		user = this.userService.findUserByEmail("admin@gmail.com");
		assertEquals("admin@gmail.com", user.getEmail());
		assertEquals(DigestUtils.sha256Hex("secret{admin@gmail.com}"), 
				user.getPassword());
		assertEquals("admin", user.getFirstName());
		assertEquals("admin", user.getLastName());
		
		user = this.userService.findUserById(3);
		assertEquals("admin@gmail.com", user.getEmail());
		assertEquals(DigestUtils.sha256Hex("secret{admin@gmail.com}"), 
				user.getPassword());
		assertEquals("admin", user.getFirstName());
		assertEquals("admin", user.getLastName());
	}
	
/* 유저 저장 */
	@Test
	public void testSave() {
		
		User user = new User();

		//새로운 계정 저장
		user.setEmail("save@gmail.com");
		user.setPassword("secret");
		user.setFirstName("save");
		user.setLastName("save");
		user.setJoin_date(new Date(1991, 3, 10, 2, 23, 34));
		targetUser = this.userService.save(user);
		assertSame(4L, targetUser.getId());
		assertEquals("save@gmail.com", targetUser.getEmail());
		assertEquals("secret", targetUser.getPassword());
		assertEquals("save", user.getFirstName());
		assertEquals("save", user.getLastName());
		assertEquals("Fri Apr 10 02:23:34 KST 3891", 
				targetUser.getJoin_date().toString());
		
		//있는 계정 저장 (수정)
		user.setId(4L);
		user.setEmail("save2@gmail.com");
		user.setPassword("secret2");
		user.setFirstName("save2");
		user.setLastName("save2");
		targetUser = this.userService.save(user);
		assertSame(4L, targetUser.getId());
		assertEquals("save2@gmail.com", targetUser.getEmail());
		assertEquals("secret2", targetUser.getPassword());
		assertEquals("save2", user.getFirstName());
		assertEquals("save2", user.getLastName());
		assertEquals("Fri Apr 10 02:23:34 KST 3891", 
				targetUser.getJoin_date().toString());
	}
	
/* 로그인 성공 테스트 */
	@Test
	public void testLoginSuccess() throws AuthenticationException {
		
		User user = userService.login("guest@gmail.com", "secret");
		
		assertEquals("guest@gmail.com", user.getEmail());
		assertEquals(DigestUtils.sha256Hex("secret{guest@gmail.com}"), 
				user.getPassword());
		assertEquals("guest", user.getFirstName());
		assertEquals("guest", user.getLastName());
	}
	
/* 로그인 실패 테스트 */
	@Test(expected = AuthenticationException.class)
	public void testLoginFailure() throws AuthenticationException {
		
		userService.login("guest@gmail.com", "fail");
	}
	
/* 로그인 실패 코드 테스트 */
	@Test
	public void testLoginFailureCode()  {
		
		//password incorrect
		try {
			
			userService.login("guest@gmail.com", "fail");
		
		} catch (AuthenticationException e) {
			
			assertEquals("invalid.password", e.getCode());
		}	
		
		//email incorrect
		try {
			
			userService.login("fail", "secret");
		
		} catch (AuthenticationException e) {
			
			assertEquals("invalid.email", e.getCode());
		}
	}
	
	@After
	public void verify() {
		
		/*Mockito.verify(userRepository, VerificationModeFactory.times(1))
				.findUserByEmail(Mockito.anyString());
		
		Mockito.reset(userRepository);*/
	}
}
