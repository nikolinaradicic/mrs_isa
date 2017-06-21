package mrs.app.domain.restaurant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class ItemDrink {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@ManyToOne
	private Drink drink;

	@Column(nullable = true)
	private int quantity;

	@Column
	private String status;
	
	@Column
	private boolean bill;

	@ManyToOne
	private WaiterOrd waiterOrd;

	public ItemDrink() {
		// TODO Auto-generated constructor stub
		this.status = "Not Accepted";
		this.bill=false;
	}

	public ItemDrink(Drink drink, int quantity, String status) {
		super();
		this.drink = drink;
		this.quantity = quantity;
		this.status = "Not Accepted";
	}

	public ItemDrink(Drink drink, int quantity) {
		super();
		this.drink = drink;
		this.quantity = quantity;
		this.status = "Not Accepted";
	}
	
	

	public boolean isBill() {
		return bill;
	}

	public void setBill(boolean bill) {
		this.bill = bill;
	}

	@JsonIgnore
	public WaiterOrd getWaiterOrd() {
		return waiterOrd;
	}

	public void setWaiterOrd(WaiterOrd waiterOrd) {
		this.waiterOrd = waiterOrd;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Drink getDrink() {
		return drink;
	}

	public void setDrink(Drink drink) {
		this.drink = drink;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ItemDrink))
			return false;
		ItemDrink c = (ItemDrink) obj;
		if (this.id == c.id)
			return true;
		return false;
	}

}
