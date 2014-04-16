package springsource.web.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

@Entity
@Table(name = "PERMISSION", uniqueConstraints = 
		{ @UniqueConstraint(columnNames = { "PERMISSION" }) })
public class Permission implements Serializable {

	private static final long serialVersionUID = -1638794138457715802L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(columnDefinition = "int", nullable = false)
	private Long id;
	
	@Column(columnDefinition = "varchar", unique = true, nullable = false)
	private String permission;

/* Constructor */
	Permission() {
		// Form ORM
	}

	public Permission(String permission) {
		
		this.permission = permission;
	}

/* get set */
	public Long getId() {
		return id;
	}
	
	public String getPermission() {
		return permission;
	}
	
	public void setPermission(String permission) {
		this.permission = permission;
	}
}
