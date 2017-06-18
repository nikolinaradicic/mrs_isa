package mrs.app.repository;

import java.util.Collection;

import mrs.app.domain.Waiter;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.WaiterOrd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface OrderRepository extends JpaRepository<WaiterOrd, Long> {
	@Query("select w from WaiterOrd w where w.restaurant = ?1")
	Collection<WaiterOrd> findByRest(Restaurant r);
	
	@Query("select w from WaiterOrd w where w.waiter=?1")
	Collection<WaiterOrd> findByWaiter(Waiter w);
	
}


