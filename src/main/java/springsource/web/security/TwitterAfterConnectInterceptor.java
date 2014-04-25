package springsource.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;

public class TwitterAfterConnectInterceptor 
		implements ConnectInterceptor<Twitter> {
	
	private static final Logger log = 
			LoggerFactory.getLogger(TwitterAfterConnectInterceptor.class);
	
	@Override
	public void preConnect(ConnectionFactory<Twitter> connection,
			MultiValueMap<String, String> parameters, WebRequest request) {
		
		log.info("twitter pre");
	}
	
	@Override
	public void postConnect(
			Connection<Twitter> connection, WebRequest request) {
		
		log.info("twitter post");
	}
}
