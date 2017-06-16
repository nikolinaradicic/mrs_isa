package mrs.app.domain.restaurant;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Version;

@Entity
public class GroceryList {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@ManyToOne
	private Restaurant restaurant;
	
	@OneToMany(cascade={CascadeType.ALL},fetch= FetchType.EAGER,mappedBy="groceryList")
	private Set<Offer> offers;
	
	@Version
	@Column(name="version")
	private Long version;
	
	@OneToOne
	@JoinColumn(nullable=true)
	private Offer acceptedOffer;
	
	@Column(nullable=false)
	private Date startDate;
	
	@Column(nullable=false)
	private Date endDate;
	
	@Column(nullable=false)
	private String text;
	public GroceryList() {
		super();
		// TODO Auto-generated constructor stub
		offers = new HashSet<Offer>();
	}
	
	

	public GroceryList(Restaurant restaurant, Date startDate, Date endDate,
			String text) {
		super();
		this.restaurant = restaurant;
		this.startDate = startDate;
		this.endDate = endDate;
		this.text = text;
		offers = new HashSet<Offer>();
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


	public Set<Offer> getOffers() {
		return offers;
	}

	public void setOffers(Set<Offer> offers) {
		this.offers = offers;
	}

	public Offer getAcceptedOffer() {
		return acceptedOffer;
	}

	public void setAcceptedOffer(Offer acceptedOffer) {
		this.acceptedOffer = acceptedOffer;
	}

	public Date getStartDate() {
		return startDate;
	}

	public void setStartDate(Date startDate) {
		this.startDate = startDate;
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



	public Long getVersion() {
		return version;
	}



	public void setVersion(Long version) {
		this.version = version;
	}

	

}
