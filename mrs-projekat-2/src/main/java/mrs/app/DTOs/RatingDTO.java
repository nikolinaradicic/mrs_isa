package mrs.app.DTOs;

import java.util.HashMap;

public class RatingDTO {
	private double rating;
	
	private HashMap<String, Double> waiters;
	
	private HashMap<String, Double> meals;
	
	public RatingDTO(){
		waiters = new HashMap<String, Double>();
		meals = new HashMap<String, Double>();
	}

	public double getRating() {
		return rating;
	}

	public void setRating(double rating) {
		this.rating = rating;
	}

	public HashMap<String, Double> getWaiters() {
		return waiters;
	}

	public void setWaiters(HashMap<String, Double> waiters) {
		this.waiters = waiters;
	}

	public HashMap<String, Double> getMeals() {
		return meals;
	}

	public void setMeals(HashMap<String, Double> d) {
		this.meals = d;
	}
	
	
	
	
}
