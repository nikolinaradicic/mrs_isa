package mrs.app.service;

import java.util.Collection;

import mrs.app.domain.restaurant.Drink;
import mrs.app.domain.restaurant.Meal;
import mrs.app.domain.restaurant.Restaurant;


public interface RestaurantService {
	
	Collection<Restaurant> findAll();

	Restaurant create(Restaurant restaurant) throws Exception;

	Drink addDrink(Drink drink) throws Exception;

	Meal addMeal(Meal meal) throws Exception;

	int changeInformation(Restaurant restaurant);
	
	Restaurant findOne(Long id);
}
