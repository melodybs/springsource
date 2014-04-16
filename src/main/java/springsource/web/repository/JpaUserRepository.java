package springsource.web.repository;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import springsource.web.domain.User;

/**
 * {@link UserRepository} 
 */
@Repository("userRepository")
public class JpaUserRepository implements UserRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public User findUserById(long id) {
		
		return this.entityManager.find(User.class, id);
	}
	
	@Override
	public User findUserByEmail(String email) {
		String hql = "SELECT u FROM User u WHERE u.email = :email";
		
		TypedQuery<User> query = 
				this.entityManager.createQuery(hql, User.class)
						.setParameter("email", email);
		List<User> users = query.getResultList();
		
		return users.size() == 1 ? users.get(0) : null;
	}
	
	@Override
	public User save(User user) {
		
		if (user.getId() != null) {
			
			return this.entityManager.merge(user);
		} else {
			
			this.entityManager.persist(user);
			
			return user;
		}
	}
}


