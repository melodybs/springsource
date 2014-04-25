package springsource.web.security;


import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.social.security.SocialUser;
import org.springframework.social.security.SocialUserDetails;
import org.springframework.social.security.SocialUserDetailsService;

public class SimpleSocialUsersDetailService 
		implements SocialUserDetailsService {
	
	private static final Logger log = 
			LoggerFactory.getLogger(SimpleSocialUsersDetailService.class);
	
	private UserDetailsService userDetailsService;
	
	public SimpleSocialUsersDetailService(
			UserDetailsService userDetailsService) {
		
		this.userDetailsService = userDetailsService;
	}
	
	@Override
	public SocialUserDetails loadUserByUserId(String username)
			throws UsernameNotFoundException, DataAccessException {
		
		log.debug("Loading user by user id: {}", username);
		
		UserDetails userDetails =
				userDetailsService.loadUserByUsername(username);
		
		log.debug("Found user details: {}", userDetails);
		
		List<GrantedAuthority> grantedAuthorities =
				new ArrayList<GrantedAuthority>();
		
		grantedAuthorities.add(new SimpleGrantedAuthority("ROLE_AUTHOR"));
		grantedAuthorities.add(new SimpleGrantedAuthority("PERM_ADD_POST"));
		grantedAuthorities.add(new SimpleGrantedAuthority("PERM_MOD_POST"));
		
		return new SocialUser(userDetails.getUsername(), 
				userDetails.getPassword(), grantedAuthorities);
		//return (SocialUserDetails) userDetails; 
	}
}
