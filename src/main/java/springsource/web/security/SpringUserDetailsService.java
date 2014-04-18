package springsource.web.security;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import springsource.web.domain.Permission;
import springsource.web.domain.Role;
import springsource.web.domain.User;
import springsource.web.service.UserService;

@Component
public class SpringUserDetailsService implements UserDetailsService {

	@Autowired
	private UserService userService;

	@Override
	public UserDetails loadUserByUsername(String email)
			throws UsernameNotFoundException {
		
		if (StringUtils.isBlank(email)) {
			
			throw new UsernameNotFoundException("아이디를 입력해 주시기 바랍니다.");
		}
		
		User user = userService.findUserByEmail(email);
		
		if (user == null) {
			
			throw new UsernameNotFoundException("일치하는 아이디가 없습니다: " + email);
		}
		
		List<GrantedAuthority> grantedAuthorities =
				new ArrayList<GrantedAuthority>();
		
		for (Role role : user.getRoles()) {
				
			grantedAuthorities.add(new SimpleGrantedAuthority(role.getRole()));
			
			for (Permission permission : role.getPermissions()) {
				
				grantedAuthorities.add(
						new SimpleGrantedAuthority(permission.getPermission()));
			}
		}
		
		return new SpringUserDetails(
				userService.findUserByEmail(email), grantedAuthorities);
	}
}
