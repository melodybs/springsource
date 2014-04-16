package springsource.web.service;

import java.util.List;

import springsource.web.domain.SpringPosts;

public interface SpringPostsService {
	
	SpringPosts findSpringPostById(long id);
	
	List<SpringPosts> findMainPosts();
	
	List<SpringPosts> findAllPosts();
	
	List<SpringPosts> findPossiblePrevPosts(long id_main);
	
	SpringPosts findNextMainSpringPost(long id);
	
	int deleteSpringPost(SpringPosts springPosts);
	
	int storeSpringPost(SpringPosts springPosts);
	
	int modifySpringPost(SpringPosts springPosts); 
	
	/*String findPostAsJsonString (String postName, long postId);*/
}
