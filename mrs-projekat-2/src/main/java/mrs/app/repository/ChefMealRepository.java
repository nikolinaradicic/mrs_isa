package mrs.app.repository;

import java.util.Collection;
import java.util.List;

import mrs.app.domain.User;
import mrs.app.domain.UserType;
import mrs.app.domain.restaurant.BartenderDrink;
import mrs.app.domain.restaurant.ChefMeal;
import mrs.app.domain.restaurant.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChefMealRepository extends JpaRepository<ChefMeal, Long>{

	
	@Query("select c from ChefMeal c where c.restaurant = ?1")
	Collection<ChefMeal> findByRest(Restaurant r);
}
