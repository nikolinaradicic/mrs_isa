package mrs.app.domain;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Inheritance;
import javax.persistence.InheritanceType;
import javax.persistence.Transient;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class User implements Serializable{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(nullable = false)
	protected String password;
	
	@Transient
	protected String repeatedPassword;
	
	private String imagePath;
	
	public String getImagePath() {
		return imagePath;
	}

	public void setImagePath(String imagePath) {
		this.imagePath = imagePath;
	}


	@Id
	@GeneratedValue
	protected Long id;
	
	@Column(nullable = false)
	protected String name;
	@Column(nullable = false)
	protected String lastname;
	@Column(nullable = false,unique=true)
	protected String email;
	@Column(nullable=false)
	@Enumerated(EnumType.STRING)
	protected UserType role;
	public User(){
		
	}

	public User(String password, String name, String lastname, String email) {
		super();
		this.password = password;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
	}
	
	

	public User(String password, String name, String lastname, String email,
			UserType role) {
		super();
		this.password = password;
		this.name = name;
		this.lastname = lastname;
		this.email = email;
		this.role = role;
	}

	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getLastname() {
		return lastname;
	}
	public void setLastname(String lastname) {
		this.lastname = lastname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getRepeatedPassword() {
		return repeatedPassword;
	}
	public void setRepeatedPassword(String repeatedPassword) {
		this.repeatedPassword = repeatedPassword;
	}

	public UserType getRole() {
		return role;
	}

	public void setRole(UserType role) {
		this.role = role;
	}

	
	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof User))
			return false;
		User c = (User) obj;
		if (this.id == c.id)
			return true;
		return false;
	}

	
}
