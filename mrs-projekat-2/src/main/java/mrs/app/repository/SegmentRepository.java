package mrs.app.repository;

import java.util.Collection;

import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.RestaurantTable;
import mrs.app.domain.restaurant.Segment;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface SegmentRepository extends JpaRepository<Segment, Long> {
	
	public Segment findByNameAndRestaurant(String name, Restaurant restaurant);
	
	@Modifying
	@Query("update Segment s set s.chart = ?1 where s.id = ?2")
	@Transactional
	int updateSegment(String chart, Long id);
	
	Collection<Segment> findByRestaurant(Restaurant r); 	

}
