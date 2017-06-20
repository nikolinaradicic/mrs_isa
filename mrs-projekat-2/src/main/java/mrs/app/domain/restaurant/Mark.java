package mrs.app.domain.restaurant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

@Entity
public class Mark {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long Id;
	
	@Column
	private int markMeals;
	
	@Column
	private int markService;
	
	@Column
	private int markRestaurant;
	
	@OneToOne
	private Visit visit;
	
	public Mark() {
		// TODO Auto-generated constructor stub
	}

	public Long getId() {
		return Id;
	}

	public void setId(Long id) {
		Id = id;
	}

	public int getMarkMeals() {
		return markMeals;
	}

	public void setMarkMeals(int markMeals) {
		this.markMeals = markMeals;
	}

	public int getMarkService() {
		return markService;
	}

	public void setMarkService(int markService) {
		this.markService = markService;
	}

	public int getMarkRestaurant() {
		return markRestaurant;
	}

	public void setMarkRestaurant(int markRestaurant) {
		this.markRestaurant = markRestaurant;
	}

	public Visit getVisit() {
		return visit;
	}

	public void setVisit(Visit visit) {
		this.visit = visit;
	}
	
	
	
	

}
