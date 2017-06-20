package mrs.app.repository;

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
	
	
}
