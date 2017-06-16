package mrs.app.domain.restaurant;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import mrs.app.domain.Guest;

@Entity
public class Reservation implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Guest guest;
	
	@Column(nullable=false)
	private Date date;
	

	@Column(nullable=false)
	private String startTime;
	
	@Column(nullable=false)
	private String endTime;
	
	@ManyToOne
	private Restaurant restaurant;
	
	@OneToMany
	private Set<Invitation> invited;

	
	@ManyToMany
	private Set<RestaurantTable> restaurantTable;
	
	@Column 
	private int brojStolica;
	
	public Reservation(){
		super();
	}
	

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	


	public int getBrojStolica() {
		return brojStolica;
	}


	public void setBrojStolica(int brojStolica) {
		this.brojStolica = brojStolica;
	}


	public Restaurant getRestaurant() {
		return restaurant;
	}


	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}


	public Set<Invitation> getInvitation() {
		return invited;
	}


	public void setInvitation(Set<Invitation> invited) {
		this.invited= invited;
	}


	public Set<RestaurantTable> getRestaurant_table() {
		return restaurantTable;
	}


	public void setRestaurant_table(Set<RestaurantTable> restaurant_table) {
		this.restaurantTable = restaurant_table;
	}
	
	

	
	
	
	

}
