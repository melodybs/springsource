package springsource.web.security;

import org.springframework.social.connect.Connection;
import org.springframework.social.connect.ConnectionFactory;
import org.springframework.social.connect.web.ConnectInterceptor;
import org.springframework.social.facebook.api.Facebook;
import org.springframework.util.MultiValueMap;
import org.springframework.web.context.request.WebRequest;

public class FacebookAfterConnectInterceptor 
		implements ConnectInterceptor<Facebook> {

	@Override
	public void preConnect(ConnectionFactory<Facebook> connection,
			MultiValueMap<String, String> parameters, WebRequest request) {
		// TODO Auto-generated method stub
	}
	
	@Override
	public void postConnect(
			Connection<Facebook> connection, WebRequest request) {
		// TODO Auto-generated method stub
		
	}
}
