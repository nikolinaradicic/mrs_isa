package mrs.app.domain;
import javax.persistence.Entity;
import javax.persistence.ManyToOne;

@Entity
public class RestaurantManager extends User{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@ManyToOne
	private Restaurant restaurant;

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

}
