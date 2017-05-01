package mrs.app.domain;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Restaurant {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private String description;
	
	@Transient
	private Set<Meal> menu;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	private Set<Drink> drinkList;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	private Set<RestaurantManager> managers;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public Set<Meal> getMenu() {
		return menu;
	}
	
	public void setMenu(Set<Meal> menu) {
		this.menu = menu;
	}
	
	public Long getId() {
		return id;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	@JsonIgnore
	public Set<RestaurantManager> getManagers() {
		return managers;
	}
	@JsonIgnore
	public void setManagers(Set<RestaurantManager> managers) {
		this.managers = managers;
	}

	public Set<Drink> getDrinkList() {
		return drinkList;
	}

	public void setDrinkList(Set<Drink> drinkList) {
		this.drinkList = drinkList;
	}

}
