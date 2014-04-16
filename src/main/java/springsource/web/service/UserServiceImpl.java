package springsource.web.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import springsource.web.domain.User;
import springsource.web.repository.UserRepository;

/**
 * {@link UserService}인터페이스를 구현하는 클래스.
 * 
 * @see {@link UserService}
 * @author melodybs
 *
 */
@Service
@Transactional(readOnly = true) //읽기전용으로 설정한다.
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	@Transactional(readOnly = false) //쓰기도 가능하게 메소드 단위로 설정.
	public User save(User user) {
		
		return this.userRepository.save(user);
	}

	@Override
	public User login(String email, String password) 
			throws AuthenticationException {
		
		User user = this.userRepository.findUserByEmail(email);
		
		if (user != null) {
			
			String pwd = DigestUtils.sha256Hex(password + "{" + email + "}");
			
			if (!user.getPassword().equals(pwd)) { //equalsIgnoreCase
				
				throw new AuthenticationException(
						"비밀번호가 정확하지 않습니다.", "invalid.password");
			}
		} else {
			throw new AuthenticationException(
					"이메일이 정확하지 않습니다.", "invalid.email"); //invalid.username
		}
		
		return user;
	}
	
	@Override
	public User findUserByEmail(String email) {
		
		return this.userRepository.findUserByEmail(email);
	}
	
	@Override
	public User findUserById(long id) {
		
		return this.userRepository.findUserById(id);
	}
}
