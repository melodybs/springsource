package springsource.web.domain.support;

import java.util.Date;

import org.apache.commons.codec.digest.DigestUtils;

import springsource.web.domain.Permission;
import springsource.web.domain.Role;
import springsource.web.domain.User;

/**
 * {@link User}
 */
public class UserBuilder extends EntityBuilder<User> {
	
	@Override
	void initProduct() {
		
		this.product = new User();
	}
	
	public UserBuilder credentials(String email, String password) {
		
		this.product.setEmail(email);
		this.product.setPassword(
				DigestUtils.sha256Hex(password + "{" + email + "}"));
		
		return this;
	}
	
	public UserBuilder firstName(String firstName) {
		
		this.product.setFirstName(firstName);
		
		return this;
	}
	
	public UserBuilder lastName(String lastName) {
		
		this.product.setLastName(lastName);
		
		return this;
	}
	
	public UserBuilder join_date(Date join_date) {
		
		this.product.setJoin_date(join_date);
		
		return this;
	}
	
	public UserBuilder roleWithPermission(Role role, Permission... permissions) {
		
		this.product.getRoles().add(role);
		
		for (Permission permission : permissions) {
			
			role.getPermissions().add(permission);
		}
		
		return this;
	}
	
	@Override
	User assembleProduct() {
		
		return this.product;
	}
}
