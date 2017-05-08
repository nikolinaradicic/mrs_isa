package mrs.app.service;

import mrs.app.domain.restaurant.RestaurantTable;

public interface TableService {
	
	RestaurantTable create(RestaurantTable table) throws Exception;

}
