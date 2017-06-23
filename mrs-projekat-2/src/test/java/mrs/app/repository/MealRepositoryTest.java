package mrs.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import mrs.app.domain.restaurant.Meal;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.repository.MealRepository;
import mrs.app.repository.RestaurantRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value = "test")
public class MealRepositoryTest {
	@Autowired
	MealRepository mealRepository;
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Test
	public void executesQueryMethodsCorrectly()
	{
		Restaurant restaurant = new Restaurant("restoran", "opis");
		Restaurant saved = restaurantRepository.save(restaurant);
		Meal meal = new Meal("jelo", "opis", 200, saved);
		mealRepository.save(meal);
		
		List<Meal> all = mealRepository.findAll();
		assertThat(all.size()).isGreaterThan(0);
	}

}
