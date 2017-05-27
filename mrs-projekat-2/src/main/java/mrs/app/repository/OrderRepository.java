package mrs.app.repository;

import mrs.app.domain.restaurant.Order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
	
}


