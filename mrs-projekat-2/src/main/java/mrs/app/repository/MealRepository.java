package mrs.app.repository;

import mrs.app.domain.restaurant.Meal;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MealRepository extends JpaRepository<Meal, Long> {

}
