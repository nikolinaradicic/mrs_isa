package mrs.app.domain;

import java.sql.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.WorkingShift;

@Entity
public class Employee extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Date birthday;
	protected int uniformSize;
	protected int shoeSize;
	
	@OneToMany(mappedBy = "employee")
	protected Set<WorkingShift> shifts;
	
	@ManyToOne
	protected Restaurant restaurant;
	
	public Employee() {
		// TODO Auto-generated constructor stub
	}
	
	public Employee(Date birthday, int uniformSize, int shoeSize, Restaurant restaurant) {
		super();
		this.birthday = birthday;
		this.uniformSize = uniformSize;
		this.shoeSize = shoeSize;
		this.restaurant = restaurant;
	}
	
	
	
	public Employee(String password, String name, String lastname,
			String email, UserType role,Date birthday, int uniformSize, int shoeSize, Restaurant restaurant) {
		super(password, name, lastname, email, role);
		// TODO Auto-generated constructor stub
		this.birthday = birthday;
		this.uniformSize = uniformSize;
		this.shoeSize = shoeSize;
		this.restaurant = restaurant;
	}

	public Date getBirthday() {
		return birthday;
	}
	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}
	public int getUniformSize() {
		return uniformSize;
	}
	public void setUniformSize(int uniformSize) {
		this.uniformSize = uniformSize;
	}
	public int getShoeSize() {
		return shoeSize;
	}
	public void setShoeSize(int shoeSize) {
		this.shoeSize = shoeSize;
	}
	public Restaurant getRestaurant() {
		return restaurant;
	}
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

}
