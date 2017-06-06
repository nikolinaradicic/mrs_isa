package mrs.app.DTOs;


public class OfferDTO {
	
	private Long groceryListId;
	
	private double price;
	
	private String message;

	private String restaurant;
	
	public OfferDTO(){
		
	}
	
	
	public OfferDTO(Long groceryListId, double price, String message) {
		super();
		this.price = price;
		this.message = message;
		this.groceryListId = groceryListId;
	}



	public Long getGroceryListId() {
		return groceryListId;
	}


	public void setGroceryListId(Long groceryListId) {
		this.groceryListId = groceryListId;
	}


	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


	public String getRestaurant() {
		return restaurant;
	}


	public void setRestaurant(String restaurant) {
		this.restaurant = restaurant;
	}


}
