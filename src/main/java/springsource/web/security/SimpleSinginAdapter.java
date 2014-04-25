package springsource.web.security;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.savedrequest.RequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.social.connect.Connection;
import org.springframework.social.connect.web.SignInAdapter;
import org.springframework.web.context.request.NativeWebRequest;

public class SimpleSinginAdapter implements SignInAdapter {
	
	private static final Logger log = 
			LoggerFactory.getLogger(SimpleSinginAdapter.class);
	
	private final RequestCache requestCache;
	
	@Inject
	public SimpleSinginAdapter(RequestCache requestCache) {
		
		this.requestCache = requestCache;
	}
	
	@Override
	public String signIn(String localUserId, 
			Connection<?> connection, NativeWebRequest request) {
		
		log.info("signIn: " + connection + " " + request);
		
		SecurityContextHolder.getContext().setAuthentication(
				new UsernamePasswordAuthenticationToken(
						localUserId, null, null));
		
		return extractOriginalUrl(request);
	}
	
	private String extractOriginalUrl(NativeWebRequest request) {
		
		log.info("extractOriginalUrl: " + request);

		HttpServletRequest nativeReq = 
				request.getNativeRequest(HttpServletRequest.class);
		HttpServletResponse nativeRes =
				request.getNativeResponse(HttpServletResponse.class);
		
		SavedRequest saved = requestCache.getRequest(nativeReq, nativeRes);
		
		requestCache.removeRequest(nativeReq, nativeRes);
		
		removeAuthenticationAttributes(nativeReq.getSession(false));
		
		return saved.getRedirectUrl();
	}
	
	private void removeAuthenticationAttributes(HttpSession session) {
		
		log.info("removeAuthenticationAttributes: " + session);
		
		if (session == null) {
			
			return;
		}
		
		session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
	}
}
