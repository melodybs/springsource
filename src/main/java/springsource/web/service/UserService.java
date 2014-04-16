package springsource.web.service;

import springsource.web.domain.User;

/**
 * Contract for services that work with a {@link User}
 * 
 * @author melodybs
 *
 */
public interface UserService {
	
	/**
	 * {@link User}도메인 객체를 받아 데이터베이스에 저장한다.
	 * 
	 * @param user
	 * @return {@link Usesr}
	 */
	User save(User user);
	
	/**
	 * 로그인 로직을 처리. 
	 * {@link User}를 검색해서 패스워드를 확인하고 정확하다면 {@link User}, 
	 * 없거나 정확하지 않다면 {@link AuthenticationException}을 리턴.
	 * 
	 * @param username the username
	 * @param password the password
	 * @return the user
	 * @throws AuthenticationException if user not found or incorrect password
	 */
	User login(String username, String password) throws AuthenticationException;
	
	/**
	 * 이메일으로 {@link User}객체를 가져온다.
	 * 
	 * @param username
	 * @return {@link User}
	 */
	User findUserByEmail(String email);
	
	/**
	 * id로  {@link User}객체를 가져온다.
	 * 
	 * @param id
	 * @return {@link User}
	 */
	User findUserById(long id);
}
