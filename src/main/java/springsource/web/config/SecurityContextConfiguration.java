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
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.social.security.SocialUserDetailsService;
import org.springframework.social.security.SpringSocialConfigurer;

import springsource.web.security.SimpleSocialUserDetailsService;
import springsource.web.service.UserService;

@Configuration
@EnableWebSecurity
public class SecurityContextConfiguration extends WebSecurityConfigurerAdapter {

	@Autowired
	private ApplicationContext context;
	
	@Autowired
	private UserService userService;
	
	@Override
	public void configure(WebSecurity web) throws Exception {
		
		web.ignoring().antMatchers("/resources/**");
	}
	
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
			.formLogin()
				.loginPage("/signin")
				.loginProcessingUrl("/signin/authenticate")
				.failureUrl("/signin?param.error=bad_credentials")
			.and()
				.logout()
					.logoutUrl("/signout")
					.deleteCookies("JSESSIONID")
			.and()
				.authorizeRequests()
					.antMatchers("/admin/**", "/favicon.ico", "/resources/**", "/auth/**", "/signin/**", "/signup/**", "/disconnect/facebook").permitAll()
					.antMatchers("/**").authenticated()
			.and()
				.rememberMe()
			.and()
				.apply(new SpringSocialConfigurer());
	}
	
	protected void registerAuthentication(AuthenticationManagerBuilder auth) 
			throws Exception {
		
		auth.userDetailsService(userDetailsService())
				.passwordEncoder(passwordEncoder());
	}
	
	@Bean
	public PasswordEncoder passwordEncoder() {
		
		return new BCryptPasswordEncoder(10);
	}
	
	@Bean
	public SocialUserDetailsService socialUserDetailsService() {
		
		return new SimpleSocialUserDetailsService(userDetailsService());
	}
}
