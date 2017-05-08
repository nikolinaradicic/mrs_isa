package mrs.app.repository;

import mrs.app.domain.restaurant.Drink;

import org.springframework.data.jpa.repository.JpaRepository;

public interface DrinkRepository extends JpaRepository<Drink, Long> {

}
