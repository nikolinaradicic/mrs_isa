package mrs.app.repository;

import java.util.Date;

import mrs.app.domain.restaurant.Bill;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface CheckRepository extends JpaRepository<Bill, Long> {

}
