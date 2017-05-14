package mrs.app.repository;

import java.util.Collection;
import java.util.Date;

import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.WorkingShift;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface WorkingShiftRepository extends JpaRepository<WorkingShift, Long> {

	@Query("select w from WorkingShift w where w.restaurant = ?1 and w.date between ?2 and ?3")
	Collection<WorkingShift> findShifts(Restaurant r, Date start, Date end);
}
