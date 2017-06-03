package mrs.app.repository;

import java.util.Collection;
import java.util.List;

import mrs.app.domain.User;
import mrs.app.domain.UserType;
import mrs.app.domain.restaurant.ChefMeal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ChefMealRepository extends JpaRepository<ChefMeal, Long>{

}
