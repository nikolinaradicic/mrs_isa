package mrs.app.repository;

import java.util.Collection;

import mrs.app.domain.Employee;
import mrs.app.domain.restaurant.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {
	
	public Collection<Employee> findByRestaurant(Restaurant restaurant);
}
