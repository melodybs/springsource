package springsource.web.domain;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

@Entity
@Table(name = "USER")
public class User implements Serializable {

	private static final long serialVersionUID = -595797749313267230L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(columnDefinition = "int", nullable = false)
	private Long id;
	
	@Email
	@NotEmpty
	@Column(columnDefinition = "varchar", nullable = false)
	private String email;
	
	@NotEmpty
	@Column(columnDefinition = "char default 64", nullable = false)
	private String password;
	
	@NotEmpty
	@Column(columnDefinition = "varchar", nullable = false)
	private String name;
	
	@NotEmpty
	@Column(columnDefinition = "datetime", nullable = false)
	private Date join_date;
	
	@ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	@JoinTable(name="USER_ROLE", joinColumns={@JoinColumn(name="user_id", referencedColumnName = "id")}, inverseJoinColumns={@JoinColumn(name="role_id", referencedColumnName = "id")})
	private List<Role> roles = new ArrayList<Role>();

/* Constructor */
	public User() {
	
	}	
	
/* getter and setter */
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Date getJoin_date() {
		return join_date;
	}

	public void setJoin_date(Date join_date) {
		this.join_date = join_date;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}
}
