package mrs.app.repository;

import mrs.app.domain.restaurant.ItemMeal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ItemMealRepository extends JpaRepository<ItemMeal, Long> {

	@Modifying
	@Query("update ItemMeal r set r.quantity=?1 where r.id=?2")
	@Transactional
	int updateItemMeal(int quantity,Long id);
	
	@Modifying
	@Query("update ItemMeal r set r.status=?1 where r.id=?2")
	@Transactional
	int updateItemMealStatus(String status,Long id);
	
	
}