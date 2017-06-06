package mrs.app.service;

import java.util.Collection;
import java.util.Date;

import mrs.app.domain.RestaurantManager;
import mrs.app.domain.restaurant.GroceryList;

public interface GroceryListService {
	public GroceryList create(GroceryList gl) throws Exception;
	public Collection<GroceryList> findByManager(RestaurantManager user, Date date);
	public GroceryList findOne(Long id);
	Collection<GroceryList> findAllActive(Date start);
	
}
