package mrs.app.repository;

import mrs.app.domain.restaurant.Bill;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckRepository extends JpaRepository<Bill, Long> {

}
