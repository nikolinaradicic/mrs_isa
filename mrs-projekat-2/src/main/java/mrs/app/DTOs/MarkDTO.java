package mrs.app.DTOs;

public class MarkDTO {
	
	private int meal_rank;
	private int restaurant_mark;
	private int service_mark;
	private Long visit_id;
	
	public MarkDTO() {
		// TODO Auto-generated constructor stub
	}
	
	public int getMeal_rank() {
		return meal_rank;
	}
	public void setMeal_rank(int meal_rank) {
		this.meal_rank = meal_rank;
	}
	public int getRestaurant_mark() {
		return restaurant_mark;
	}
	public void setRestaurant_mark(int restaurant_mark) {
		this.restaurant_mark = restaurant_mark;
	}
	public int getService_mark() {
		return service_mark;
	}
	public void setService_mark(int service_mark) {
		this.service_mark = service_mark;
	}
	public Long getVisit_id() {
		return visit_id;
	}
	public void setVisit_id(Long visit_id) {
		this.visit_id = visit_id;
	}
	
	

}
