package mrs.app.DTOs;

import java.util.Date;

import mrs.app.domain.restaurant.Offer;
import mrs.app.domain.restaurant.Restaurant;

public class GroceryListDTO {
	private Long id;
	
	private Restaurant restaurant;

	private Offer offer;

	private Date endDate;
	
	private String text;
	
	public GroceryListDTO(){}
	
	

	public GroceryListDTO(Long id, Restaurant restaurant, Offer offer,
			Date endDate, String text) {
		super();
		this.id = id;
		this.restaurant = restaurant;
		this.offer = offer;
		this.endDate = endDate;
		this.text = text;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Restaurant getRestaurant() {
		return restaurant;
	}

	public void setRestaurant(Restaurant restaurant) {
		this.restaurant = restaurant;
	}

	public Offer getOffer() {
		return offer;
	}

	public void setOffer(Offer offer) {
		this.offer = offer;
	}

	public Date getEndDate() {
		return endDate;
	}

	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}
	
	

}
