package mrs.app.repository;

import mrs.app.domain.restaurant.Shift;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ShiftRepository extends JpaRepository<Shift, Long> {

}
