package mrs.app.domain.restaurant;

import java.io.Serializable;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Segment implements Serializable{
	
	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@ManyToOne
	private Restaurant restaurant;
	
	@OneToMany(cascade={CascadeType.ALL},fetch= FetchType.LAZY,mappedBy="segment")
	private Set<RestaurantTable> tables;
	
	@Column(columnDefinition = "LONGTEXT")
	private String chart;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
	public Restaurant getRestaurant() {
		return restaurant;
	}

	@JsonIgnore
	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Set<RestaurantTable> getTables() {
		return tables;
	}

	public void setTables(Set<RestaurantTable> tables) {
		this.tables = tables;
	}

	public Segment() {}

	public Segment(String name, Restaurant restaurant) {
		// TODO Auto-generated constructor stub
		super();
		this.name = name;
		this.restaurant = restaurant;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getChart() {
		return chart;
	}

	public void setChart(String chart) {
		this.chart = chart;
	}

	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Segment))
			return false;
		Segment c = (Segment) obj;
		if (this.id == c.id)
			return true;
		return false;
	}
}
