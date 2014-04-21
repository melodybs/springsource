package springsource.web.config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.social.UserIdSource;
import org.springframework.social.config.annotation.ConnectionFactoryConfigurer;
import org.springframework.social.config.annotation.EnableSocial;
import org.springframework.social.config.annotation.SocialConfigurerAdapter;
import org.springframework.social.connect.ConnectionFactoryLocator;
import org.springframework.social.connect.ConnectionRepository;
import org.springframework.social.connect.UsersConnectionRepository;
import org.springframework.social.connect.jdbc.JdbcUsersConnectionRepository;
import org.springframework.social.connect.web.ConnectController;
import org.springframework.social.facebook.connect.FacebookConnectionFactory;
import org.springframework.social.twitter.connect.TwitterConnectionFactory;

@Configuration
@EnableSocial
@PropertySource("classpath:/social.properties")
public class SocialContextConfiguration  extends SocialConfigurerAdapter {
	
	@Autowired
	private DataSource dataSource;
	
	@Override
	public void addConnectionFactories(
			ConnectionFactoryConfigurer cfConfigure, Environment env) {
		
		//FaceBook
		cfConfigure.addConnectionFactory(
				new FacebookConnectionFactory(
						env.getProperty("facebook.key"), 
						env.getProperty("facebook.secret")));
		
		//Twitter
		cfConfigure.addConnectionFactory(
				new TwitterConnectionFactory(
						env.getProperty("twitter.key"), 
						env.getProperty("twitter.secret")));
	}
	
	@Override
	public UserIdSource getUserIdSource() {
		
		return new UserIdSource() {
			
			@Override
			public String getUserId() {
				Authentication authentication = 
						SecurityContextHolder.getContext().getAuthentication();
				
				if (authentication == null) {
					
					throw new IllegalStateException("Unable to get a "
							+ "ConnectionRepository: no user signed in");
				}
				
				return authentication.getName();
			}
		};
		//return new AuthenticationNameUserIdSource();	
	}
	
	@Override
	public UsersConnectionRepository getUsersConnectionRepository(
			ConnectionFactoryLocator connectionFactoryLocator) {
		
		return new JdbcUsersConnectionRepository(
				dataSource, connectionFactoryLocator, Encryptors.noOpText());
	}
	
	@Bean
	public ConnectController connectController(
			ConnectionFactoryLocator connectionFactoryLocator,
			ConnectionRepository connectionRepository) {
		
		ConnectController connectController = new ConnectController(
				connectionFactoryLocator, connectionRepository);
		
		return connectController;
	}
}
