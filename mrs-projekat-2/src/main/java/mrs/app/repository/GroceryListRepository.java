package mrs.app.repository;

import java.util.Collection;
import java.util.Date;
import mrs.app.domain.restaurant.GroceryList;
import mrs.app.domain.restaurant.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface GroceryListRepository extends JpaRepository<GroceryList, Long> {
	
	@Query("select g from GroceryList g where g.restaurant = ?1 and g.endDate > ?2 and g.acceptedOffer is NULL")
	Collection<GroceryList> findActive(Restaurant r, Date date);

	@Query("select g from GroceryList g where g.startDate <= ?1 and g.endDate >= ?1 and g.acceptedOffer is NULL")
	Collection<GroceryList> findAllActive(Date now);

	@Query("select g from GroceryList g where g.restaurant = ?1 and (g.endDate < ?2 or g.acceptedOffer is not NULL)")
	Collection<GroceryList> findPast(Restaurant restaurant, Date date);
}
