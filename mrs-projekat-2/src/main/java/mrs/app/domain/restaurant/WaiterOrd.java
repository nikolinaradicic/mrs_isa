package mrs.app.domain.restaurant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import mrs.app.domain.Waiter;

import org.hibernate.mapping.Set;


@Entity
public class WaiterOrd implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	

	@ManyToMany(cascade={CascadeType.ALL},fetch = FetchType.LAZY)
	public Collection<ItemMeal> meals;
	
	@ManyToMany(cascade={CascadeType.ALL},fetch = FetchType.LAZY)
	public Collection<ItemDrink> drinks;
	
	@ManyToOne
	private Restaurant restaurant;
	
	@ManyToOne
	private Waiter waiter;
	
	@OneToOne
	private RestaurantTable table;

	public WaiterOrd() {
		// TODO Auto-generated constructor stub
		this.meals=new ArrayList<ItemMeal>();
		this.drinks=new ArrayList<ItemDrink>();
		
	}
	
	public WaiterOrd(Collection<ItemMeal> meals, Collection<ItemDrink> drinks,Restaurant restaurant){
		this.meals=meals;
		this.drinks=drinks;
		this.restaurant=restaurant;
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

	public Collection<ItemDrink> getDrinks() {
		return drinks;
	}

	public void setDrinks(Collection<ItemDrink> drinks) {
		this.drinks = drinks;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Waiter getWaiter() {
		return waiter;
	}

	public void setWaiter(Waiter waiter) {
		this.waiter = waiter;
	}

	public RestaurantTable getTable() {
		return table;
	}

	public void setTable(RestaurantTable table) {
		this.table = table;
	}
	
	
}
