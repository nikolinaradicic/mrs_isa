package mrs.app.repository;

import mrs.app.domain.restaurant.Meal;
import mrs.app.domain.restaurant.Restaurant;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface MealRepository extends JpaRepository<Meal, Long> {

	@Modifying
	@Query("update Meal r set r.name = ?1, r.description = ?2, r.price = ?3 where r.id = ?4")
	@Transactional
	int updateMeal(String name, String description, double price, Long id);
	
	@Modifying
	@Query("update Meal r set r.deleted = ?1 where r.id = ?2 and r.restaurant=?3")
	@Transactional
	int updateDeleteMeal(boolean b, Long id, Restaurant r);
}
