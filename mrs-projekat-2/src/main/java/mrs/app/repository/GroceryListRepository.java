package mrs.app.repository;

import java.util.Collection;
import java.util.Date;

import mrs.app.domain.RestaurantManager;
import mrs.app.domain.restaurant.GroceryList;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroceryListRepository extends JpaRepository<GroceryList, Long> {
	
	@Query("select g from GroceryList g where g.manager = ?1 and g.endDate > ?2")
	Collection<GroceryList> findActive(RestaurantManager rm, Date date);

}
