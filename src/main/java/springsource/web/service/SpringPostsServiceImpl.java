package springsource.web.service;

import java.util.HashMap;
import java.util.List;

import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Service;

import springsource.web.domain.SpringPosts;
import springsource.web.repository.SpringPostsRepository;

@Service("springService")
public class SpringPostsServiceImpl implements SpringPostsService {

	@Autowired
	private SpringPostsRepository springPostsRepository;
	
	@Override
	public SpringPosts findSpringPostById(long id) {
		
		return this.springPostsRepository.findSpringPostById(id);
	}
	
	@Override
	public List<SpringPosts> findMainPosts() {
	
		return this.springPostsRepository.findMainPosts();
	}
	
	@Override
	public List<SpringPosts> findAllPosts() {
		
		return this.springPostsRepository.findAllPosts();
	}
	
	@Override
	public SpringPosts findNextMainSpringPost(long id) {
		
		return this.springPostsRepository.findNextMainSpringPost(id);
	}
	
	@Override
	public List<SpringPosts> findPossiblePrevPosts(long id_main) {
		return this.springPostsRepository.findPossiblePrevPosts(id_main);
	}

	@Override
	public int deleteSpringPost(SpringPosts springPosts) {
		
		return this.springPostsRepository
				.deleteSpringPost(springPosts);
	}
	
	@Override
	public int storeSpringPost(SpringPosts springPosts) {
		
		return this.springPostsRepository.storeSpringPost(springPosts);
	}
	
	@Override
	public int modifySpringPost(SpringPosts springPosts) {
		
		return this.springPostsRepository.modifySpringPost(springPosts);
	}
	
	/*@Override
	public String findPostAsJsonString(String postName, long postId) {
		
		String json = null;
		HashMap<String, String> result = new HashMap<String, String>();
		ObjectMapper map = new ObjectMapper();
		SpringPosts post = 
				this.springPostsRepository.findSpringPostById(postId);

		result.put("id", post.getId().toString());
		result.put("id_main", post.getId_main().toString());
		result.put("id_prev", post.getId_prev().toString());
		result.put("id_next", post.getId_next().toString());
		result.put("title", post.getTitle());
		result.put("contents_ko", post.getContents_ko());
		result.put("contents_en", post.getContents_en());
		result.put("title_sub", post.getTitle_sub());
		result.put("examples", post.getExamples());
		result.put("writer", post.getWriter());
		
		if (!result.isEmpty()) {
			try {
				json = map.writeValueAsString(result);
			} catch (Exception e) {
				result.put("result", "false");
				result.put("message", e.getMessage());
				result.put("data", null);
			}
		}
		
		return json;
	}*/
}
