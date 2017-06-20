package mrs.app.repository;

import mrs.app.domain.restaurant.Visit;

import org.springframework.data.jpa.repository.JpaRepository;

public interface VisitRepository extends JpaRepository<Visit, Long>{

}
