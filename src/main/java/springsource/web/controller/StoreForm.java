package springsource.web.controller;

import java.io.Serializable;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Null;

import org.hibernate.validator.constraints.NotEmpty;
import org.openid4java.message.Message;
import org.springframework.binding.message.MessageBuilder;
import org.springframework.binding.message.MessageContext;
import org.springframework.binding.validation.ValidationContext;

public class StoreForm implements Serializable {
	
	//private SpringPosts springPosts;
	
	@NotNull
	@Min(1)
	private long id_main;
	
	@NotNull
	@Min(1)
	private long id_prev;
	
	@NotEmpty
	private String title;
	
	@Null
	private String contents_ko;
	
	@Null
	private String title_sub;
	
	@Null
	private String examples;
	/*	
	    private long id;
		private long id_next;
		private String writer;
		private Timestamp last_modified;
	*/

/* Method */
	// validate + State(SelectCategory) 형태의 메소드를 구현 -> 이러한 이름 규약을 가진 메소드는 자동으로 찾아 유효성을 검증한다.
	/*public void validateSelectCategory(ValidationContext context) {
		
		if (context.getUserEvent().equals("next")) {
			
			MessageContext messages = context.getMessageContext();
			
			if (category == null) {
				
				messages.addMessage(
						new MessageBuilder().error().source("category")
								.code("error.page.category.required").build());
			}
		}
	}*/
	
/* Getter Setter */
	public long getId_main() {
		return id_main;
	}

	public void setId_main(long id_main) {
		this.id_main = id_main;
	}

	public long getId_prev() {
		return id_prev;
	}

	public void setId_prev(long id_prev) {
		this.id_prev = id_prev;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContents_ko() {
		return contents_ko;
	}

	public void setContents_ko(String contents_ko) {
		this.contents_ko = contents_ko;
	}

	public String getTitle_sub() {
		return title_sub;
	}

	public void setTitle_sub(String title_sub) {
		this.title_sub = title_sub;
	}

	public String getExamples() {
		return examples;
	}

	public void setExamples(String examples) {
		this.examples = examples;
	}
}
