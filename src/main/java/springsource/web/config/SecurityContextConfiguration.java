package springsource.web.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.UserIdSource;
import org.springframework.social.security.AuthenticationNameUserIdSource;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import springsource.web.security.SimpleSocialUsersDetailService;
import springsource.web.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityContextConfiguration extends WebSecurityConfigurerAdapter {
	
	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private UserService userService;

	@Autowired
	public void registerAuthentication(AuthenticationManagerBuilder auth) 
			throws Exception {
		
		auth.userDetailsService(userDetailsService());
	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		
		web.ignoring().antMatchers("/resources/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.formLogin()
				.loginPage("/public/authentication/login")
				.loginProcessingUrl("/j_spring_security_check")
				.failureUrl("/public/authentication/login?error=bad_credentials")
				.defaultSuccessUrl("/main")
			.and()
				.logout()
					.logoutUrl("/j_spring_security_logout")
					.deleteCookies("JSESSIONID")
			.and()
				.authorizeRequests()
					.antMatchers("/secured/admin/write/**", 
							     "/secured/admin/modify/**",
							     "/secred/**/*").authenticated()
					.antMatchers("/**/*").permitAll()
			.and()
				.rememberMe()
			.and()
				.apply(new SpringSocialConfigurer());
	}

	@Bean
	public SocialUserDetailsService socialUserDetailsService() {
		
		return new SimpleSocialUsersDetailService(userDetailsService());
	}
	
	@Bean UserIdSource userIdSource() {
		
		return new AuthenticationNameUserIdSource();
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return NoOpPasswordEncoder.getInstance();
	}
	
	@Bean
	public TextEncryptor textEncryptor() {
		
		return Encryptors.noOpText();
	}
}
