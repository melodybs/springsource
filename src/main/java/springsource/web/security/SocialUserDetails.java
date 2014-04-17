package springsource.web.security;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUser;

import springsource.web.domain.Role;


public class SocialUserDetails extends SocialUser {
	
	private long id;
	private String firstName;
	private String lastName;
	private Role role;
	private SocialMediaService socialSignInProvider;

	public SocialUserDetails(String username, String password, 
			Collection<? extends GrantedAuthority> authorities) {
		
		super(username, password, authorities);
	}
	
	public static class Builder {
		
		private long id;
		private String username;
		private String firstName;
		private String lastName;
		private String password;
		private Role role;
		private SocialMediaService socialSignInProvider;
		private Set<GrantedAuthority> authorityies;
		
		public Builder() {
			
			this.authorityies = new HashSet<>();
		}
		
		public Builder username(String username) {
			
			this.username = username;
			
			return this;
		}
		
		public Builder firstName(String firstName) {
			
			this.firstName = firstName;
			
			return this;
		}

		public Builder lastName(String lastName) {
			
			this.lastName = lastName;
			
			return this;
		}

		public Builder password(String password) {
			
			this.password = password;
			
			return this;
		}
		
		public Builder role(Role role) {
			
			this.role = role;
			
			SimpleGrantedAuthority authority = 
					new SimpleGrantedAuthority(role.toString());
			
			this.authorityies.add(authority);
			
			return this;
		}
		
		public Builder socialSignInProvider(
				SocialMediaService socialSignInProvider) {
			
			this.socialSignInProvider = socialSignInProvider;
			
			return this;
		}
	}
}
