package springsource.web.security;

import java.util.Collection;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.social.security.SocialUser;

import springsource.web.domain.Permission;
import springsource.web.domain.Role;

public class SocialUserDetails extends SocialUser {
	
/* variables */
	private Long id;
	
	private String username;
	
	private String password;
	
	private String firstName;
	
	private String lastName;
	
	private Date join_date;
	
	private List<Role> roles;

/* constructor */
	public SocialUserDetails(String username, String password, 
			Collection<? extends GrantedAuthority> authorities) {
		
		super(username, password, authorities);
	}
	
/* builder */
	public static Builder getBuilder() {
		
		return new Builder();
	}

	public static class Builder {
		
		private Long id;
		
		private String username;
		
		private String password;
		
		private String firstName;
		
		private String lastName;
		
		private Date join_date;
		
		private List<Role> roles;
		
		private Set<GrantedAuthority> authorities;
		
		public Builder() {
			
			this.authorities = new HashSet<>();
		}
		
		public Builder id(Long id) {
			
			this.id = id;
			
			return this;
		}
		
		public Builder username(String username) {
			
			this.username = username;
			
			return this;
		}
		
		public Builder password(String password) {
			
			this.password = password;
			
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
		
		public Builder join_date(Date join_date) {
			
			this.join_date = join_date;
			
			return this;
		}
		
		public Builder roles(List<Role> roles) {
			
			this.roles = roles;
			
			for (Role role : roles) {
				
				this.authorities.add(
						new SimpleGrantedAuthority(role.getRole()));
				
				for (Permission permission : role.getPermissions()) {
					
					this.authorities.add(new SimpleGrantedAuthority(
							permission.getPermission()));
				}
			}
			
			return this;
		}
		
		public SocialUserDetails build() {
			
			SocialUserDetails user = 
					new SocialUserDetails(username, password, authorities);
			
			user.id = id;
			user.firstName = firstName;
			user.lastName = lastName;
			user.join_date = join_date;
			user.roles = roles;
			
			return user;
		}
	}
	
/* Not used */
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

	
/* getter setter */
	public Long getId() {
		
		return id;
	}

	public String getUsername() {
		
		return username;
	}

	public String getFirstName() {
		
		return firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public Date getJoin_date() {
		return join_date;
	}

	public List<Role> getRoles() {
		return roles;
	}
}
