package mrs.app.service;

import java.util.Collection;

import mrs.app.domain.Drink;
import mrs.app.domain.Restaurant;


public interface RestaurantService {
	
	Collection<Restaurant> findAll();

	Restaurant create(Restaurant restaurant) throws Exception;

	Drink addDrink(Drink drink) throws Exception;
}
