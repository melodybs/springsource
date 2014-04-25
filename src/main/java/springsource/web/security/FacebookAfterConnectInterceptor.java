package springsource.web.security;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;

public class FacebookAfterConnectInterceptor 
		implements ConnectInterceptor<Facebook> {
	
	private static final Logger log = 
			LoggerFactory.getLogger(FacebookAfterConnectInterceptor.class);

	@Override
	public void preConnect(ConnectionFactory<Facebook> connection,
			MultiValueMap<String, String> parameters, WebRequest request) {
		
		log.info("facebook pre");
	}
	
	@Override
	public void postConnect(
			Connection<Facebook> connection, WebRequest request) {
		
		log.info("facebook post");
	}
}
