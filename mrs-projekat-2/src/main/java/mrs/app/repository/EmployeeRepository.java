package mrs.app.repository;

import java.util.Collection;

import mrs.app.domain.Employee;
import mrs.app.domain.Waiter;
import mrs.app.domain.restaurant.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	public Collection<Employee> findByRestaurant(Restaurant restaurant);
	
	

	@Query("select w from Employee w where w.restaurant = ?1 and w.role = 'ROLE_WAITER'")
	public Collection<Waiter> findWaiters(Restaurant restaurant);
}
