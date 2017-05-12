package mrs.app.service;

import java.util.List;

import mrs.app.DTOs.TableDTO;
import mrs.app.domain.restaurant.RestaurantTable;
import mrs.app.domain.restaurant.Segment;

public interface TableService {
	
	RestaurantTable create(RestaurantTable table) throws Exception;
	
	boolean saveTables(List<TableDTO> tables, Segment segment);

	RestaurantTable findByNameAndSegment(String name, Segment segment);
}
