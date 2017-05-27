package mrs.app.repository;

import java.util.Collection;

import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.Shift;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepository extends JpaRepository<Shift, Long> {

	Collection<Shift> findByRestaurant(Restaurant restaurant);

}
