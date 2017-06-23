package mrs.app.domain.restaurant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import com.fasterxml.jackson.annotation.JsonIgnore;

import mrs.app.domain.Bidder;

@Entity
public class Offer {
	
	public Offer(Bidder bidder, GroceryList groceryList, double price,
			String message, boolean accepted) {
		super();
		this.bidder = bidder;
		this.groceryList = groceryList;
		this.price = price;
		this.message = message;
		this.accepted = accepted;
	}

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private Bidder bidder;
	
	@ManyToOne
	@JoinColumn(nullable=false)
	private GroceryList groceryList;
	
	@Column(nullable=false)
	private double price;
	
	@Column(nullable=true)
	private String message;
	
	@Column(nullable=false)
	private boolean accepted;

	public Offer() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Bidder getBidder() {
		return bidder;
	}

	public void setBidder(Bidder bidder) {
		this.bidder = bidder;
	}

	@JsonIgnore
	public GroceryList getGroceryList() {
		return groceryList;
	}

	public void setGroceryList(GroceryList groceryList) {
		this.groceryList = groceryList;
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

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}

}
