package mrs.app.domain.restaurant;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import mrs.app.domain.Guest;

@Entity
public class Reservation implements Serializable{
	

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	private Guest guest;
	
	@Column(nullable=false)
	private Date date;
	

	@Column(nullable=false)
	private String startTime;
	
	@Column(nullable=false)
	private String endTime;
	
	@Column(nullable=false)
	private Set<Guest> invatedFriends;
	
	@Column(nullable=false)
	private Set<RestaurantTable> reservedTable;
	
	public Reservation(){
		super();
	}
	

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public String getStartTime() {
		return startTime;
	}

	public void setStartTime(String startTime) {
		this.startTime = startTime;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Set<Guest> getInvatedFriends() {
		return invatedFriends;
	}

	public void setInvatedFriends(Set<Guest> invatedFriends) {
		this.invatedFriends = invatedFriends;
	}

	public Set<RestaurantTable> getReservedTable() {
		return reservedTable;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public void setReservedTable(Set<RestaurantTable> reservedTable) {
		this.reservedTable = reservedTable;
	}

	
	
	
	

}
