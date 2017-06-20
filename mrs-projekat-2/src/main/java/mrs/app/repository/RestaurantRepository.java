package mrs.app.repository;

import java.util.List;

import mrs.app.domain.restaurant.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	public List<Restaurant> findAll();
	
	public List<Restaurant> findAllByOrderByNameAsc();
	
	@Modifying
	@Query("update Restaurant r set r.name = ?1, r.description = ?2 where r.id = ?3")
	@Transactional
	int updateRestaurant(String name, String description, Long id);

	@Modifying
	@Query("update Restaurant r set r.longitude = ?2, r.latitude = ?3 where r.id = ?1")
	@Transactional
	int updateLocation(Long id, String longitude, String latitude);
}
