package mrs.app.service;

import java.util.Collection;
import java.util.Date;
import mrs.app.domain.restaurant.GroceryList;
import mrs.app.domain.restaurant.Restaurant;

public interface GroceryListService {
	public GroceryList create(GroceryList gl) throws Exception;
	public GroceryList findOne(Long id);
	Collection<GroceryList> findAllActive(Date start);
	Collection<GroceryList> findByRestaurant(Restaurant r, Date date);
	
}
