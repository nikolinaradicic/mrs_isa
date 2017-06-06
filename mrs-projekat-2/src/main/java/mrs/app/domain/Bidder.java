package mrs.app.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;

import com.fasterxml.jackson.annotation.JsonIgnore;

import mrs.app.domain.restaurant.Offer;

@Entity
public class Bidder extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Bidder() {
		// TODO Auto-generated constructor stub
		this.role = UserType.ROLE_BIDDER;
		this.firstTime="notvisited";
	}
	
	@OneToMany(cascade={CascadeType.ALL},fetch=FetchType.LAZY,mappedBy="bidder")
	private Set<Offer> offers;

	@JsonIgnore
	public Set<Offer> getOffers() {
		return offers;
	}

	public void setOffers(Set<Offer> offers) {
		this.offers = offers;
	}
	
	
}
