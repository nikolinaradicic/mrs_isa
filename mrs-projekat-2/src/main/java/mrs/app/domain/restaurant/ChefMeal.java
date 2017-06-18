package mrs.app.domain.restaurant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

@Entity
public class ChefMeal implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	

	@ManyToMany(cascade={CascadeType.ALL},fetch = FetchType.LAZY)
	public Collection<ItemMeal> meals;
	
	@ManyToOne
	Restaurant restaurant;
 
	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public ChefMeal(){
		this.meals=new ArrayList<ItemMeal>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<ItemMeal> getMeals() {
		return meals;
	}

	public void setMeals(Collection<ItemMeal> meals) {
		this.meals = meals;
	}
	
	
}
