package mrs.app.domain;

import java.util.Date;

import javax.persistence.Entity;

import mrs.app.domain.restaurant.Restaurant;

@Entity
public class Waiter extends Employee{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Waiter() {
		// TODO Auto-generated constructor stub
		this.role = UserType.ROLE_WAITER;
	}

	public Waiter(Date birthday, int uniformSize, int shoeSize,
			Restaurant restaurant, String username, String lastname, String gmail,String password) {
		super(birthday, uniformSize, shoeSize, restaurant);
		// TODO Auto-generated constructor stub
		this.role = UserType.ROLE_WAITER;
		super.setEmail(gmail);
		super.setLastname(lastname);
		super.setName(username);
		super.setPassword(password);
	}
	
	
}
