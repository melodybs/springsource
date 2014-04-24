package springsource.web.security;

import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.web.DisconnectInterceptor;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.web.context.request.WebRequest;

public class FacebookDisconnectInterceptor 
		implements DisconnectInterceptor<Facebook> {

	@Override
	public void preDisconnect(
			ConnectionFactory<Facebook> connection, WebRequest request) {
		// TODO Auto-generated method stub
		
	}
	
	@Override
	public void postDisconnect(
			ConnectionFactory<Facebook> connection, WebRequest request) {
		// TODO Auto-generated method stub
		
	}
}
