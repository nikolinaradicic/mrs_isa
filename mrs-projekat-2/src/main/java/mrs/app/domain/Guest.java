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
	private Set<Guest> friends;
	
	@OneToMany
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private Set<Guest> requests;

	public Set<Guest> getRequests() {
		return requests;
	}

	public void setRequests(Set<Guest> requests) {
		this.requests = requests;
	}
	
	public Set<Guest> getFriends() {
		return friends;
	}

	public void setFriends(Set<Guest> friends) {
		this.friends =friends;
	}

	public Guest() {
		// TODO Auto-generated constructor stub
		this.role=UserType.GUEST;
	}
}
