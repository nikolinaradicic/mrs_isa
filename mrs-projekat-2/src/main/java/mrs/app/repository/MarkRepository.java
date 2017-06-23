package mrs.app.repository;
import java.util.Collection;

import mrs.app.domain.Waiter;
import mrs.app.domain.restaurant.Mark;
import mrs.app.domain.restaurant.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface MarkRepository extends JpaRepository<Mark, Long>{

	@Query("select avg(w.markRestaurant) from Mark w where w.visit.reservation.restaurant = ?1")
	double sumForRestaurant(Restaurant restaurant);

	@Query("select avg(w.markService) from Mark w where w.visit.bill.order.waiter = ?1")
	double sumForWaiter(Waiter waiter);
	
	@Query("select w from Mark w where w.visit.reservation.restaurant = ?1")
	Collection<Mark> findByRestaurant(Restaurant rest);
	
	
	
}
