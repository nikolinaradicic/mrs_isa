package mrs.app.repository;

import java.util.Collection;

import mrs.app.domain.restaurant.BartenderDrink;
import mrs.app.domain.restaurant.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BartenderDrinkRepository extends JpaRepository<BartenderDrink, Long>{

	@Query("select b from BartenderDrink b where b.restaurant = ?1")
	Collection<BartenderDrink> findByRest(Restaurant r);
}
