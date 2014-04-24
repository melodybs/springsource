package springsource.web.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.connect.web.DisconnectInterceptor;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;

public class TwitterDisconnectInterceptor 
		implements DisconnectInterceptor<Twitter> {
	
	@Override
	public void preDisconnect(
			ConnectionFactory<Twitter> connection, WebRequest request) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void postDisconnect(
			ConnectionFactory<Twitter> connection, WebRequest request) {
		// TODO Auto-generated method stub
		
	}
}
