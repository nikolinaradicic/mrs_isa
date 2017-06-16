package mrs.app.domain.restaurant;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import mrs.app.domain.Guest;



@Entity
public class Invitation  implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Column(nullable=false,unique=true)
	@GeneratedValue
	@Id
	private int id;
	
	@ManyToOne
	private Guest guest;
	@ManyToOne
	private Reservation reservation;
	
	@Column(nullable= false)
	private boolean accepted;
	
	public Invitation(){
		this.accepted=false;
		
	}
	@Override
	public boolean equals(Object obj){
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (!(obj instanceof Invitation))
			return false;
		Invitation p = (Invitation) obj;
		if (p.id == this.id)
			return true;
		return false;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Guest getGuest() {
		return guest;
	}

	public void setGuest(Guest guest) {
		this.guest = guest;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public boolean isAccepted() {
		return accepted;
	}

	public void setAccepted(boolean accepted) {
		this.accepted = accepted;
	}
	
	
	

}
