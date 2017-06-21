package mrs.app.domain;
import java.sql.Date;

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
			Restaurant restaurant) {
		super(birthday, uniformSize, shoeSize, restaurant);
		// TODO Auto-generated constructor stub
	}
	
	
}
