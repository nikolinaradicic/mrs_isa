package mrs.app.domain;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import mrs.app.domain.restaurant.Segment;

import org.hibernate.annotations.Cascade;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Guest extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@ManyToMany
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private List<Guest> friends;
	
	@ManyToMany
	@Cascade({org.hibernate.annotations.CascadeType.SAVE_UPDATE})
	private Set<Guest> requests;

	@JsonIgnore
	public Set<Guest> getRequests() {
		return requests;
	}

	public void setRequests(Set<Guest> requests) {
		this.requests = requests;
	}
	
	public Guest() {
		// TODO Auto-generated constructor stub
		this.role=UserType.ROLE_GUEST;
		this.firstTime="notvisited";
	}
	@JsonIgnore
	public List<Guest> getFriends() {
		return friends;
	}

	public void setFriends(List<Guest> friends) {
		this.friends = friends;
	}

	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Guest))
			return false;
		Guest c = (Guest) obj;
		if (this.id == c.id)
			return true;
		return false;
	}
}
