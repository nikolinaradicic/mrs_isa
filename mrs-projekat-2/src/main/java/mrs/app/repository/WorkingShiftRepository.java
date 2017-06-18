package mrs.app.repository;

import java.util.Collection;
import java.util.Date;

import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.Shift;
import mrs.app.domain.restaurant.WorkingShift;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface WorkingShiftRepository extends JpaRepository<WorkingShift, Long> {

	@Query("select w from WorkingShift w where w.restaurant = ?1 and w.date between ?2 and ?3")
	Collection<WorkingShift> findShifts(Restaurant r, Date start, Date end);
	
	@Query("select w from WorkingShift w where w.restaurant = ?1")
	Collection<WorkingShift> findShForRest(Restaurant r);
	
	Collection<WorkingShift> findByDateAndShiftAndRestaurant(Date date, Shift shift, Restaurant r);
	

	@Modifying
	@Query("update WorkingShift w set w.date = ?1 where w.id = ?2")
	@Transactional
	int updateWorkingShift(Date date, Long id);
}
