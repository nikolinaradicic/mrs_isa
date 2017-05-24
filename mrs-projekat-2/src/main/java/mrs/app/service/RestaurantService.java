package mrs.app.service;

import java.util.Collection;
import java.util.List;

import mrs.app.domain.restaurant.Drink;
import mrs.app.domain.restaurant.Meal;
import mrs.app.domain.restaurant.Restaurant;


public interface RestaurantService {
	
	Collection<Restaurant> findAll();

	Restaurant create(Restaurant restaurant) throws Exception;

	Drink addDrink(Drink drink) throws Exception;

	Meal addMeal(Meal meal) throws Exception;

	int changeInformation(Restaurant restaurant);
	
	List<Restaurant> findAllSort();
	
	Restaurant findOne(Long id);
}
