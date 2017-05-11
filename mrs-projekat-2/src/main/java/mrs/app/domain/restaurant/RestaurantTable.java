package mrs.app.domain.restaurant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.Transient;

@Entity
public class RestaurantTable {
	
	@Id
	@GeneratedValue
	protected Long id;

	@Column(nullable=false)
	protected int chairNumber;
	
	@ManyToOne
	private Segment segment;

	public RestaurantTable() {
		// TODO Auto-generated constructor stub
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	public Segment getSegment() {
		return segment;
	}


	public void setSegment(Segment segment) {
		this.segment = segment;
	}


	public int getChairNumber() {
		return chairNumber;
	}


	public void setChairNumber(int chairNumber) {
		this.chairNumber = chairNumber;
	}




	
	

}
