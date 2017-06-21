package mrs.app.repository;

import java.util.Collection;

import mrs.app.domain.Waiter;
import mrs.app.domain.restaurant.ItemDrink;
import mrs.app.domain.restaurant.WaiterOrd;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface ItemDrinkRepository extends JpaRepository<ItemDrink,Long>{
	
	@Modifying
	@Query("update ItemDrink r set r.quantity=?1 where r.id=?2")
	@Transactional
	int updateItemDrink(int quantity,Long id);

	@Modifying
	@Query("update ItemDrink r set r.status=?1 where r.id=?2")
	@Transactional
	int updateItemDrinkStatus(String status,Long id);
	
	@Modifying
	@Query("update ItemDrink r set r.bill=?1 where r.id=?2")
	@Transactional
	int updateItemDrinkBill(boolean b,Long id);

	@Modifying
	@Query("update ItemDrink r set r.waiterOrd=?1 where r.id=?2")
	@Transactional
	int updateOrder(WaiterOrd saved, Long id);

}
