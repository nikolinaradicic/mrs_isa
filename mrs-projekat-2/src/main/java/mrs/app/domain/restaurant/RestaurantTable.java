package mrs.app.domain.restaurant;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

import mrs.app.DTOs.TableDTO;

@Entity
public class RestaurantTable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	
	@Column(nullable = false)
	private String name;
	
	@Column(nullable = false)
	private int chairNumber;
	
	@ManyToOne
	private Segment segment;

	public RestaurantTable() {
		// TODO Auto-generated constructor stub
	}


	public RestaurantTable(TableDTO table, Segment segment) {
		// TODO Auto-generated constructor stub
		this.name = table.getName();
		this.chairNumber = table.getChairNum();
		this.segment = segment;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}

	@JsonIgnore
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


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}




	
	

}
