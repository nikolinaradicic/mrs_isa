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

import org.hibernate.mapping.Set;


@Entity
public class WaiterOrd implements Serializable{
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	

	@ManyToMany(cascade={CascadeType.ALL},fetch = FetchType.LAZY)
	public Collection<Meal> meals;
	
	@ManyToMany(cascade={CascadeType.ALL},fetch = FetchType.LAZY)
	public Collection<Drink> drinks;

	public WaiterOrd() {
		// TODO Auto-generated constructor stub
		this.meals=new ArrayList<Meal>();
		this.drinks=new ArrayList<Drink>();
	}
	
	public WaiterOrd(Collection<Meal> meals, Collection<Drink> drinks){
		this.meals=meals;
		this.drinks=drinks;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<Meal> getMeals() {
		return meals;
	}

	public void setMeals(Collection<Meal> meals) {
		this.meals = meals;
	}

	public Collection<Drink> getDrinks() {
		return drinks;
	}

	public void setDrinks(Collection<Drink> drinks) {
		this.drinks = drinks;
	}

	
}
