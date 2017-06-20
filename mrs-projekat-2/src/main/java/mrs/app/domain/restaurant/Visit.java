package mrs.app.domain.restaurant;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Visit {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@OneToOne
	private Reservation reservation;
	
	@OneToOne
	private Bill bill;
	
	@Column
	private Date date;
	
	@Column
	private boolean marked;
	
	public Visit() {
		// TODO Auto-generated constructor stub
		this.marked=false;
	}	
	
	

	public boolean isMarked() {
		return marked;
	}



	public void setMarked(boolean marked) {
		this.marked = marked;
	}



	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Reservation getReservation() {
		return reservation;
	}

	public void setReservation(Reservation reservation) {
		this.reservation = reservation;
	}

	public Bill getBill() {
		return bill;
	}

	public void setBill(Bill bill) {
		this.bill = bill;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	
}
