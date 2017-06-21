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
	
	private boolean enabled;

	public Bidder() {
		// TODO Auto-generated constructor stub
		this.role = UserType.ROLE_BIDDER;
	}
	
	public Bidder(String password, String name, String lastname, String email) {
		super(password, name, lastname, email);
		this.role = UserType.ROLE_BIDDER;
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

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	
	
}
