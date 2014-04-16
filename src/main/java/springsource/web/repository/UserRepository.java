package springsource.web.repository;

import springsource.web.domain.User;

/**
 * {@link User}
 */
public interface UserRepository {
	
	User findUserByEmail(String email);
	
	User findUserById(long id);
	
	User save(User user);
}
