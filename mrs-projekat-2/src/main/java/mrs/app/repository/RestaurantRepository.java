package mrs.app.repository;

import java.util.List;
import mrs.app.domain.Restaurant;


import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	public List<Restaurant> findAll();
}
