package springsource.web.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import springsource.web.domain.User;

public class SpringUserDetails implements UserDetails {	

	private User user;
	private List<? extends GrantedAuthority> authorities = 
			new ArrayList<GrantedAuthority>();
	
/* Constructor */
	public SpringUserDetails(User user, 
			List<? extends GrantedAuthority> authorities) {
		
		this.user = user;
		this.authorities = authorities;
	}
	
/* Method */
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		
		return authorities;
	}
	
	@Override
	public String getPassword() {
		
		return user.getPassword();
	}
	
	@Override
	public String getUsername() {
		
		return user.getEmail();
	}
	
	public User getUser() {
		
		return user;
	}

	//여기서는 밑에 네가지 항목은 사용하지 않으므로 단순히 true를 린턴하게 설정 해두었다.
	@Override
	public boolean isAccountNonExpired() {

		return true;
	}

	@Override
	public boolean isAccountNonLocked() {

		return true;
	}
	
	@Override
	public boolean isCredentialsNonExpired() {

		return true;
	}
	
	@Override
	public boolean isEnabled() {

		return true;
	}
}
