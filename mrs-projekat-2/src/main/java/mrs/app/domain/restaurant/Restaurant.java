package mrs.app.domain.restaurant;

import java.util.Set;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import mrs.app.domain.RestaurantManager;

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
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	private Set<Meal> menu;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	private Set<Drink> drinkList;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	private Set<RestaurantManager> managers;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	private Set<Segment> segments;

	private String chart;

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

	public String getChart() {
		return chart;
	}
	
	public void setChart(String chart){
		this.chart = chart;
	}

	public Set<Segment> getSegments() {
		return segments;
	}

	public void setSegments(Set<Segment> segments) {
		this.segments = segments;
	}

}
