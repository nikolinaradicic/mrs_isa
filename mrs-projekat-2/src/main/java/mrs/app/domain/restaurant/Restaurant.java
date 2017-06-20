package mrs.app.domain.restaurant;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

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
	
	private String longitude;
	
	private String latitude;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	@Cascade({org.hibernate.annotations.CascadeType.ALL})
	private Set<Meal> menu;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	@Cascade({org.hibernate.annotations.CascadeType.ALL})
	private Set<Drink> drinkList;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	@Cascade({org.hibernate.annotations.CascadeType.ALL})
	private Set<RestaurantManager> managers;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	@Cascade({org.hibernate.annotations.CascadeType.ALL})
	private Set<WorkingShift> workingShifts;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	@Cascade({org.hibernate.annotations.CascadeType.ALL})
	private Set<Segment> segments;
	
	@OneToMany(fetch = FetchType.LAZY, mappedBy = "restaurant")
	@Cascade({org.hibernate.annotations.CascadeType.ALL})
	private Set<Shift> shifts;

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public Restaurant(String name, String description) {
		super();
		this.name = name;
		this.description = description;
	}

	public Restaurant() {
		// TODO Auto-generated constructor stub
	}

	public void setDescription(String description) {
		this.description = description;
	}
	
	@JsonIgnore
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

	@JsonIgnore
	public Set<Drink> getDrinkList() {
		return drinkList;
	}

	public void setDrinkList(Set<Drink> drinkList) {
		this.drinkList = drinkList;
	}

	public Set<Segment> getSegments() {
		return segments;
	}

	public void setSegments(Set<Segment> segments) {
		this.segments = segments;
	}

	
	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Restaurant))
			return false;
		Restaurant c = (Restaurant) obj;
		if (this.id == c.id)
			return true;
		return false;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}
}
