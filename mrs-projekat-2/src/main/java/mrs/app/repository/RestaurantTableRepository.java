package mrs.app.repository;

import mrs.app.domain.restaurant.RestaurantTable;
import mrs.app.domain.restaurant.Segment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
	
	@Modifying
	@Query("update RestaurantTable t set t.chairNumber = ?1 where t.name = ?2 and t.segment = ?3")
	@Transactional
	int updateRestaurantTable(int chairNumber, String name, Segment segment);

	RestaurantTable findByNameAndSegment(String name, Segment segment);
}
