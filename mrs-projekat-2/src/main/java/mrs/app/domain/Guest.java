package mrs.app.domain;

import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

@Entity
public class Guest extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@OneToMany
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private Set<Guest> guests;

	public Set<Guest> getGuests() {
		return guests;
	}

	public void setGuests(Set<Guest> guests) {
		this.guests = guests;
	}

}
