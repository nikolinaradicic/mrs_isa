package mrs.app.domain;

import java.sql.Date;

import javax.persistence.ManyToOne;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public class Employee extends User {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected Date birthday;
	protected int uniformSize;
	protected int shoeSize;
	
	@ManyToOne
	private Restaurant restaurant;
	
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
