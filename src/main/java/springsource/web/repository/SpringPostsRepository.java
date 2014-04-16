package springsource.web.repository;

import java.util.List;

import springsource.web.domain.SpringPosts;

/**
 * {@link SpringPosts}
 * {@link JpaSpringPostsRepository}
 * {@link JpaSpringPostsRepositoryTest}
 */
public interface SpringPostsRepository {
	
	/**
	 * 아이디로 해당글을 찾는다.
	 * 
	 * @param {@link long} id 찾을 글의 아이디.
	 * @return {@link SpringPosts}
	 */
	SpringPosts findSpringPostById(long id);
	
	/**
	 * 메인 글(id_main == 0)을 오름차순으로 찾는다.
	 * 
	 * @return List<{@link SpringPosts}>
	 */
	List<SpringPosts> findMainPosts();
	
	/**
	 * 모든 글을 오름차순으로 찾는다.
	 * 
	 * @return List<{@link SpringPosts}>
	 */
	List<SpringPosts> findAllPosts();
	
	/**
	 * 다음 메인글을 찾는다.
	 * 
	 * @param {@link SpringPosts}
	 * @return {@link SpringPosts}
	 */
	SpringPosts findNextMainSpringPost(long id);
	
	/**
	 * 
	 */
	List<SpringPosts> findPossiblePrevPosts(long id_main);
	
	/**
	 * 글을 삭제하고, 삭제한 글의 이전글 다음글의 포인터를 변경해준다(연결 리스트)
	 * 메인글을 삭제하면 서브글이 모두 함께 삭제 된다. 첫번째글은 삭제 불가능 하다.
	 * 
	 * 반환값
	 * 0 = 정상적으로 삭제 완료
	 * 1 = 첫번째글은 삭제 불가
	 * 2 = 메인글 삭제시도시 서브글로 인해서 삭제 불가.
	 * 
	 * @param {@link SpringPosts}
	 * @return {@link int}
	 */
	int deleteSpringPost(SpringPosts springPosts);
	
	/**
	 * 메인글을 받아서 해당 메인글의 서브글을 모두 지운다.
	 * 
	 * 반환값
	 * 0 = 정상처리
	 * 1 = 실패
	 * 
	 * @param {@link SpringPosts}
	 * @return {@link int} 
	 */
	int deleteSubSpringPostsByMain(long id);
	
	/**
	 * 넘어온 이전글(id_prev)값을 기반으로 글을 저장하고, 
	 * 다음글 이전글의 포인터와 현재글의 포인터를 설정한다.(연결 리스트)
	 * 
	 * 반환값
	 * 0 = 정상적으로 글 저장 완료
	 * 1 = 메인글을 서브글 앞에 등록하려는 경우..
	 * 2 = 서브글을 다른 서브글들 사이에 등록하려고 하는경우.(이전글이 해당 메인 글이면 허용)
	 * 
	 * @param {@link SpringPosts}
	 * @return {@link long} int
	 */
	int storeSpringPost(SpringPosts springPosts);
	
	/**
	 * 글을 수정하고 위치가 변경되었다면 현재글의 이전글 다음글을 서로 가르키게 하고 이동한다.
	 * 위치가 변경되지 않았다면 다른값만 수정한다.
	 * 
	 * 반환값
	 * 0 = 정상적으로 수정완료.
	 * 1 = 메인글 이동 불가.
	 * 2 = 서브글이지만 이동 불가능한 위치일 경우.
	 * @param {@link SpringPosts}
	 * @return {@link int}
	 */
	int modifySpringPost(SpringPosts springPosts);
}
