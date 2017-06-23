package mrs.app.domain.restaurant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Version;

import mrs.app.domain.Chef;

import com.fasterxml.jackson.annotation.JsonIgnore;


@Entity
public class ItemMeal {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private Meal meal;
	
	@Column(nullable = true)
	private int quantity;
	
	@Column
	private String status;
	
	@ManyToOne
	private WaiterOrd waiterOrd;
	
	@Column
	private boolean bill;
	
	@ManyToOne
	private Chef chef;
	
	@Version
	@Column(name="version")
	private Long version;
	
	public Chef getChef() {
		return chef;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	@JsonIgnore
	public WaiterOrd getWaiterOrd() {
		return waiterOrd;
	}

	public void setWaiterOrd(WaiterOrd waiterOrd) {
		this.waiterOrd = waiterOrd;
	}

	public ItemMeal() {
		// TODO Auto-generated constructor stub
		this.status="Not Accepted";
		this.bill=false;
	}
	
	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public ItemMeal(Meal meal, int quantity, String status) {
		super();
		this.meal = meal;
		this.quantity = quantity;
		this.status = "Not Accepted";
	}
	
	public ItemMeal(Meal meal, int quantity) {
		super();
		this.meal = meal;
		this.quantity = quantity;
		this.status = "Not Accepted";
	}

	public boolean isBill() {
		return bill;
	}

	public void setBill(boolean bill) {
		this.bill = bill;
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

	public Meal getMeal() {
		return meal;
	}

	public void setMeal(Meal meal) {
		this.meal = meal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof ItemMeal))
			return false;
		ItemMeal c = (ItemMeal) obj;
		if (this.id == c.id)
			return true;
		return false;
	}
	
	

}
