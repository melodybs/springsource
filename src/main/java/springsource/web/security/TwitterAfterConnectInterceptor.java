package springsource.web.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.twitter.api.Twitter;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;

public class TwitterAfterConnectInterceptor 
		implements ConnectInterceptor<Twitter> {
	
	@Override
	public void preConnect(ConnectionFactory<Twitter> connection,
			MultiValueMap<String, String> parameters, WebRequest request) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void postConnect(
			Connection<Twitter> connection, WebRequest request) {
		// TODO Auto-generated method stub
		
	}
}
