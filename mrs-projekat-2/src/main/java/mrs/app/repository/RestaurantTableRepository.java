package mrs.app.repository;

import mrs.app.domain.restaurant.RestaurantTable;
import mrs.app.domain.restaurant.Segment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
	
/*	@Modifying
	@Query("update Restaurant_table t set t.chair_number = ?1 where t.id = ?2")
	@Transactional
	int updateRestaurantTable(int chairNumber, Long id);*/


	RestaurantTable findByNameAndSegment(String name, Segment segment);
}
