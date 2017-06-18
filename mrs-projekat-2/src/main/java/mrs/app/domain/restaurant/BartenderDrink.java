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

@Entity
public class BartenderDrink implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	
	
	@ManyToMany(cascade={CascadeType.ALL},fetch = FetchType.LAZY)
	public Collection<ItemDrink> drinks;
	
	@ManyToOne
	public Restaurant restaurant;
	
	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public BartenderDrink() {
		// TODO Auto-generated constructor stub
		this.drinks=new ArrayList<ItemDrink>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<ItemDrink> getDrinks() {
		return drinks;
	}

	public void setDrinks(Collection<ItemDrink> drinks) {
		this.drinks = drinks;
	}

}
