package mrs.app.service;

import java.util.Collection;
import java.util.List;

import mrs.app.domain.restaurant.Drink;
import mrs.app.domain.restaurant.Meal;
import mrs.app.domain.restaurant.Restaurant;


public interface RestaurantService {
	
	//tested
	Collection<Restaurant> findAll();

	//tested
	Restaurant create(Restaurant restaurant) throws Exception;

	//tested
	Drink addDrink(Drink drink) throws Exception;

	//tested
	Meal addMeal(Meal meal) throws Exception;

	//tested
	int changeInformation(Restaurant restaurant);
	
	List<Restaurant> findAllSort();
	
	Restaurant findOne(Long id);

	Restaurant updateLocation(Restaurant restaurant);

	Meal updateMeal(Meal meal);

	Drink updateDrink(Drink drink);

	boolean deleteMeal(Meal meal, Restaurant restaurant);

	boolean deleteDrink(Drink drink, Restaurant r);
}
