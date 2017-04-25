package mrs.app.service;

import java.util.Collection;

import mrs.app.domain.Restaurant;
import mrs.app.domain.RestaurantManager;


public interface RestaurantService {
	Collection<Restaurant> findAll();

	Restaurant create(Restaurant restaurant) throws Exception;

	Restaurant addManager(RestaurantManager manager, Long id) throws Exception;
}
