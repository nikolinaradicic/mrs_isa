package mrs.app.repository;

import mrs.app.domain.Chef;
import mrs.app.domain.restaurant.ItemMeal;
import mrs.app.domain.restaurant.WaiterOrd;

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
	@Query("update ItemMeal r set r.status=?1, r.chef=?3 where r.id=?2")
	@Transactional
	int updateItemMealStatus(String status,Long id, Chef chef);
	
	@Modifying
	@Query("update ItemMeal r set r.bill=?1 where r.id=?2")
	@Transactional
	int updateItemMealBill(boolean b,Long id);
	
	@Modifying
	@Query("update ItemMeal r set r.waiterOrd=?1 where r.id=?2")
	@Transactional
	int updateOrder(WaiterOrd order,Long id);
	
	
}
