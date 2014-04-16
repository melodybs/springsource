package springsource.web.domain.support;

import java.sql.Timestamp;
import java.util.Date;

import springsource.web.domain.SpringPosts;

/**
 * Builds {@link SpringPosts} domain objects
 */
public class SpringPostsBuilder extends EntityBuilder<SpringPosts> {
	
	private SpringPosts product;
	
	@Override
	void initProduct() {
		
		product = new SpringPosts();
	}

	@Override
	SpringPosts assembleProduct() {
		return product;
	}

/* build */
	/*public SpringPostsBuilder id(long id) {
		
		product.setId(id);
		
		return this;
	}*/	
	
	public SpringPostsBuilder id_main(long id_main) {
		
		product.setId_main(id_main);
		
		return this;
	}
	public SpringPostsBuilder id_main() {
		
		product.setId_main(0L);
		
		return this;
	}	
	
	public SpringPostsBuilder id_prev(long id_prev) {
		
		product.setId_prev(id_prev);
		
		return this;
	}
	public SpringPostsBuilder id_prev() {
		
		product.setId_prev(1L);
		
		return this;
	}	
	
	public SpringPostsBuilder id_next(long id_next) {
		
		product.setId_next(id_next);
		
		return this;
	}
	public SpringPostsBuilder id_next() {
		
		product.setId_next(1L);
		
		return this;
	}	
	
	public SpringPostsBuilder contents_ko(String contents_ko) {
		
		product.setContents_ko(contents_ko);
		
		return this;
	}	
	public SpringPostsBuilder contents_en(String contents_en) {
		
		product.setContents_en(contents_en);
		
		return this;
	}	
	
	public SpringPostsBuilder title(String title) {
		
		product.setTitle(title);
		
		return this;
	}	
	
	public SpringPostsBuilder title_sub(String title_sub) {
		
		product.setTitle_sub(title_sub);
		
		return this;
	}	
	public SpringPostsBuilder examples(String examples) {
		
		product.setExamples(examples);
		
		return this;
	}	
	
	public SpringPostsBuilder writer(String writer) {
		
		product.setWriter(writer);
		
		return this;
	}
	
}
