package mrs.app.DTOs;


public class OfferDTO {
	
	private Long groceryListId;
	
	private Long id;
	
	private double price;
	
	private String message;

	private String restaurant;
	private String status;
	
	public OfferDTO(){
		
	}
	
	
	public OfferDTO(Long groceryListId, double price, String message) {
		super();
		this.price = price;
		this.message = message;
		this.groceryListId = groceryListId;
	}



	public OfferDTO(Long id2, double price2, String message2, Long id3) {
		// TODO Auto-generated constructor stub
		this.groceryListId = id2;
		this.price = price2;
		this.message = message2;
		this.id = id3;
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


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}


}
