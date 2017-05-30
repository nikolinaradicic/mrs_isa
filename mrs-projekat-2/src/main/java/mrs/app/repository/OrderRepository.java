package mrs.app.repository;

import mrs.app.domain.restaurant.WaiterOrd;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<WaiterOrd, Long> {
	
}


