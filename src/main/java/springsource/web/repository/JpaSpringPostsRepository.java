package springsource.web.repository;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.springframework.stereotype.Repository;

import springsource.web.domain.SpringPosts;

/**
 * {@link SpringPosts}
 */
@Repository("jpaSpringPosts")
public class JpaSpringPostsRepository implements SpringPostsRepository {
	
	@PersistenceContext
	private EntityManager entityManager;
	
	@Override
	public SpringPosts findSpringPostById(long id) {
		
		return this.entityManager.find(SpringPosts.class, id);
	}
	
	@Override
	public List<SpringPosts> findMainPosts() {

		List<SpringPosts> AllSpringPosts = findAllPosts();
		List<SpringPosts> MainSpringPosts = new ArrayList<SpringPosts>();
		
		for (SpringPosts post : AllSpringPosts) {
			
			if (post.getId_main() == 0) {
				
				MainSpringPosts.add(post);
			}
		}
		
		return MainSpringPosts;
	}
	
	@Override
	public List<SpringPosts> findAllPosts() {
	
		List<SpringPosts> AllSpringPostsList = new ArrayList<SpringPosts>();
		SpringPosts firstPost = findSpringPostById(1L);
		long next_id = firstPost.getId_next();
		
		AllSpringPostsList.add(firstPost);
		
		while (next_id != 1) {
		
			SpringPosts tempPosts =
					this.entityManager.find(SpringPosts.class, next_id);
			next_id = tempPosts.getId_next();
			
			AllSpringPostsList.add(tempPosts);
		}
		
		return AllSpringPostsList;
	}
	
	@Override
	public SpringPosts findNextMainSpringPost(long id) {

		List<SpringPosts> allMainSpringPosts = findMainPosts();
		int len = allMainSpringPosts.size();
		
		
		for (int i = 0; i < len; i++) {
			
			if (allMainSpringPosts.get(i).getId() == id) {
				
				//현재글이 마지막 메인글이라면 첫번째글 리턴. 아니라면 다음 메인글 리턴.
				return (i + 1) == len  ? allMainSpringPosts.get(0) 
						: allMainSpringPosts.get(i + 1);
			}
		}
		
		return null;
	}
	
	@Override
	public List<SpringPosts> findPossiblePrevPosts(long id_main) {
		
		List<SpringPosts> postsList = new ArrayList<SpringPosts>();
		List<SpringPosts> allPostsList = findAllPosts();
		
		//메인글인 경우
		if (id_main == 0) {
			
			for (SpringPosts post : allPostsList) {
				
				SpringPosts nextPost = findSpringPostById(post.getId_next());
				
				if (nextPost.getId_main() == 0) {
					
					postsList.add(post);
				}
			}
		//서브글인 경우
		} else {
			
			for (SpringPosts post : allPostsList) {
				
				if (post.getId_main() == id_main || post.getId() == id_main) {
					
					postsList.add(post);
				}
			}
		}
		
		return postsList;
	}
	
	@Override
	public int deleteSpringPost(SpringPosts springPosts) {
		
		long id = springPosts.getId();
		long id_main = springPosts.getId_main();
		long id_prev = springPosts.getId_prev();
		long id_next = springPosts.getId_next();
		//삭제글의 이전글
		SpringPosts prevPost = findSpringPostById(id_prev);
		//삭제글의 다음글
		SpringPosts nextPost = findSpringPostById(id_next);
		
		if (id == 1) {
			
			return 1; //첫번째글 삭제 불가.
		//메인 글 이라면 서브글 전부 같이 삭제 한다.
		} else if (id_main == 0) {
			
			//삭제글의 다음 메인글 
			SpringPosts nextMainPost = findNextMainSpringPost(id);
			long nextMainPost_id = nextMainPost.getId();
			
			//서브글을 삭제한다.
			deleteSubSpringPostsByMain(id);

			//삭제글의 이전글을 삭제글의 다음 메인글을 가르키게 한다.
			prevPost.setId_next(nextMainPost_id);
			this.entityManager.merge(prevPost);
			//샂제글의 다음 메인글이 삭제글의 이전글을 가르키게 한다.
			nextMainPost.setId_prev(id_prev);
			this.entityManager.merge(prevPost);
			
		//서브글이라면 서브글만 삭제한다.
		} else {
		
			prevPost.setId_next(id_next);
			this.entityManager.merge(prevPost);	
			nextPost.setId_prev(id_prev);
			this.entityManager.merge(nextPost);
		}
		
		//현재글 삭제 
		this.entityManager.remove(springPosts);
		
		return 0;//정상 처리
	}
	
	@Override
	public int deleteSubSpringPostsByMain(long id) {
		
		String hql = "SELECT s FROM SpringPosts s WHERE id_main = :id";
		
		TypedQuery<SpringPosts> query = 
				this.entityManager.createQuery(hql, SpringPosts.class);
		query.setParameter("id", id);
		
		List<SpringPosts> targetPostsList = query.getResultList();
		
		for (SpringPosts post : targetPostsList) {
			
			this.entityManager.remove(post);
		}
		
		return targetPostsList.size();
		/* not working. test later. 
		String hql = "DELETE FROM SpringPosts WHERE id_main = :id";
		
		int affectedRows = this.entityManager.createQuery(hql)
				.setParameter("id", id).executeUpdate();
		
		return affectedRows;*/
	}
	
	@Override
	public int storeSpringPost(SpringPosts springPosts) {
		
		//현재글의 이전글 아이디
		long id_prev = springPosts.getId_prev();
		//현재글의 메인아이디
		long id_main = springPosts.getId_main();
		//현재글의 이전글
		SpringPosts prevPost = findSpringPostById(id_prev);
		//현재글의 이전글의 메인아이디
		long prevPost_id_main = prevPost.getId_main();
		//이전글의 다음글 아이디(현재글이 이대상을 가르키기 위해서)
		long id_next = prevPost.getId_next();
		//현재글의 다음글 아이디
		SpringPosts nextPost = findSpringPostById(id_next);
		//현재글의 다음글 메인아이디
		long nextPost_id_main = nextPost.getId_main();
		
		//메인글을 서브글 앞에 등록하려는 경우.
		if (id_main == 0 && nextPost_id_main != 0) {
			
			return 1;
		}
		
		
		//서브글이고 앞글이 메인글이 맞다면 다른서브글 검사 하지 않음
		if (id_main != 0 && id_main == id_prev) {
			//empty
		} else {
			//서브글을 다른 서브글들 사이에 등록하려고 하는경우.
			if (id_main != 0 && id_main != prevPost_id_main 
					&& id_main != nextPost_id_main) {
				
				return 2;
			}
		}
		
		
		//현재글의 다음글 설정.
		springPosts.setId_next(id_next);
		//현재글 저장
		springPosts.setLast_modified(new Date());
		SpringPosts currentPost = this.entityManager.merge(springPosts);
		//현재글 아이디 
		long id_current = currentPost.getId();
		
		//이전글의 다음글 설정.
		prevPost.setId_next(id_current);
		this.entityManager.merge(prevPost);
		
		//다음글의 이전글 설정.
		nextPost.setId_prev(id_current);
		this.entityManager.merge(nextPost);
		
		return 0;//정상처리 완료 
	}
	
	@Override
	public int modifySpringPost(SpringPosts springPosts) {
		
		//현재 글
		long origin_id = springPosts.getId();
		SpringPosts currentPost = findSpringPostById(origin_id);
		long origin_main_id = currentPost.getId_main();
		long origin_prev_id = currentPost.getId_prev();
		long origin_next_id = currentPost.getId_next();
		//수정된 글
		long new_main_id = springPosts.getId_main();
		long new_prev_id = springPosts.getId_prev();
		//현재글의 이전글 다음글
		SpringPosts origin_prev_springPosts = 
				findSpringPostById(origin_prev_id);
		SpringPosts origin_next_springPosts = 
				findSpringPostById(origin_next_id);
		//수정된글의 이전글
		SpringPosts new_prev_springPosts = findSpringPostById(new_prev_id);
		Long new_next_id = new_prev_springPosts.getId_next();
		SpringPosts new_next_springPosts = findSpringPostById(new_next_id);
		//수정된글의 다음글
		
		//메인 아이디 변경 불가
		/*if (new_main_id != current_main_id) {
			return 1;
		}*/
		
		//위치가 변경되지 않은 경우
		if (new_prev_id == origin_prev_id /*&& new_next_id == origin_next_id*/) {
			
			//현재글 위치변경 없이 수정
			springPosts.setLast_modified(new Date());
			this.entityManager.merge(springPosts);
		} else { // 위치가 변경될 경우.
			
			//메인글 이라면 이동 불가
			if (origin_main_id == 0 || new_main_id == 0) {
				
				return 1;
			//서브글이라면 위치이동.
			} else {
				
				int result = storeSpringPost(springPosts);
				
				//변경불가능한 위치인경우 
				if (result != 0) {
					
					return 2;
				//변경 가능한 위치인경우 변경 한다
				} else {
					
					origin_prev_springPosts.setId_next(origin_next_id);
					this.entityManager.merge(origin_prev_springPosts);
					origin_next_springPosts.setId_prev(origin_prev_id);
					this.entityManager.merge(origin_next_springPosts);
					
					new_prev_springPosts.setId_next(origin_id);
					this.entityManager.merge(new_prev_springPosts);
					new_next_springPosts.setId_prev(origin_id);
					this.entityManager.merge(new_next_springPosts);
				}
			}
		}
		
		return 0;
	}
}
