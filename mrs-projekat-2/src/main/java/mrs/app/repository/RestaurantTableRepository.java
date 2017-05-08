package mrs.app.repository;

import mrs.app.domain.restaurant.RestaurantTable;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RestaurantTableRepository extends JpaRepository<RestaurantTable, Long> {
	public RestaurantTable findByName(String name);

}
