package springsource.web.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;

import org.hibernate.annotations.LazyCollection;
import org.hibernate.annotations.LazyCollectionOption;

@Entity
@Table(name = "ROLE", uniqueConstraints = { @UniqueConstraint(columnNames = { "ROLE" }) })
public class Role implements Serializable {

	private static final long serialVersionUID = 3446260794749532105L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(columnDefinition = "int", nullable = false)
	private Long id;
	
	@Column(columnDefinition = "varchar", unique = true, nullable = false)
	private String role;

	@ManyToMany(cascade = CascadeType.ALL)
	@JoinTable(name="ROLE_PERMISSION", joinColumns={@JoinColumn(name="role_id", referencedColumnName = "id")}, inverseJoinColumns={@JoinColumn(name="permission_id" , referencedColumnName = "id")})
	@LazyCollection(LazyCollectionOption.FALSE)
	private List<Permission> permissions = new ArrayList<Permission>();

/* Constructor */
	Role() {
		// For ORM
	}

	public Role(String role) {
		
		this.role = role;
	}

/* get set */
	public Long getId() {
		return id;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public List<Permission> getPermissions() {
		return permissions;
	}
	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}
}
