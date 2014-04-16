package springsource.web.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 *{@link Column} 속성
 *	name(""),unique(false),nullable(true),insertable(true),updatable(true),
 *	columnDefinition(""),table(""),length(255),precision(0),scale(0) 
 *
 */
@Entity
@Table( name = "SPRING_BOARD" )
public class SpringPosts implements Serializable {

	private static final long serialVersionUID = 8625763769621042011L;	
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(columnDefinition = "int", nullable = false)
	private Long id;
	@Column(columnDefinition = "int default 0", nullable = false)
	private Long id_main;
	@Column(columnDefinition = "int default 1", nullable = false)
	private Long id_prev;
	@Column(columnDefinition = "int default 1", nullable = false)
	private Long id_next;

	@Column(columnDefinition = "TEXT")
	private String contents_ko;
	@Column(columnDefinition = "TEXT")
	private String contents_en;
	@Column(columnDefinition = "varchar", nullable = false)
	private String title;
	@Column(columnDefinition = "tinytext")
	private String title_sub;
	@Column(columnDefinition = "varchar")
	private String examples;
	@Column(columnDefinition = "varchar")
	private String writer;
	@Temporal(TemporalType.TIMESTAMP)
	@Column(columnDefinition = "timestamp default CURRENT_TIMESTAMP", nullable = false, insertable = false, updatable = false)
	private Date last_modified;

    @PrePersist
    @PreUpdate
    protected void onCreateUpdate() {
    	last_modified = new Date();
    }
	
/* Constructor */
	public SpringPosts() {
	
	}

	public SpringPosts(Long id_main, Long id_sub, Long id_prev, Long id_next,
			String contents_ko, String contents_en, String title,
			String title_sub, String examples, String writer) {
		
		super();
		
		this.id_main = id_main;
		this.id_prev = id_prev;
		this.id_next = id_next;
		this.contents_ko = contents_ko;
		this.contents_en = contents_en;
		this.title = title;
		this.title_sub = title_sub;
		this.examples = examples;
		this.writer = writer;
	}

/* getter setter */
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getId_main() {
		return id_main;
	}
	public void setId_main(Long id_main) {
		this.id_main = id_main;
	}
	public Long getId_prev() {
		return id_prev;
	}
	public void setId_prev(Long id_prev) {
		this.id_prev = id_prev;
	}
	public Long getId_next() {
		return id_next;
	}
	public void setId_next(Long id_next) {
		this.id_next = id_next;
	}
	public String getContents_ko() {
		return contents_ko;
	}
	public void setContents_ko(String contents_ko) {
		this.contents_ko = contents_ko;
	}
	public String getContents_en() {
		return contents_en;
	}
	public void setContents_en(String contents_en) {
		this.contents_en = contents_en;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
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
	public String getWriter() {
		return writer;
	}
	public void setWriter(String writer) {
		this.writer = writer;
	}
	public Date getLast_modified() {
		return last_modified;
	}
	public void setLast_modified(Date last_modified) {
		this.last_modified = last_modified;
	}
}
