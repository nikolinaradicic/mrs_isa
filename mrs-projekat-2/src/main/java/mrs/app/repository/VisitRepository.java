package mrs.app.repository;

import java.util.Collection;
import java.util.Date;

import mrs.app.domain.Waiter;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.Visit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface VisitRepository extends JpaRepository<Visit, Long>{

	
	@Modifying
	@Query("update Visit v set v.marked=?1 where v.id=?2")
	@Transactional
	int updateVisit(Boolean marked,Long visit_id);

	@Query("select w from Visit w where w.bill.order.restaurant = ?1 and w.date between ?2 and ?3")
	Collection<Visit> findForDate(Restaurant restaurant, Date start, Date end);
	

	@Query("select sum(w.bill.final_price) from Visit w where w.bill.order.restaurant = ?1 and w.bill.order.waiter =?2 and w.date between ?3 and ?4")
	double sumForWaiters(Restaurant restaurant, Waiter w,  Date start, Date end);
}
