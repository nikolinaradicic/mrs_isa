package mrs.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import mrs.app.domain.restaurant.Drink;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.repository.DrinkRepository;
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
public class DrinkRepositoryTest {
	
	@Autowired
	DrinkRepository drinkRepository;
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Test
	public void executesQueryMethodsCorrectly()
	{
		Restaurant restaurant = new Restaurant("restoran", "opis");
		Restaurant saved = restaurantRepository.save(restaurant);
		Drink drink = new Drink("pice", "opis", 200, saved);
		drinkRepository.save(drink);
		
		List<Drink> drinks = drinkRepository.findAll();
		assertThat(drinks.size()).isGreaterThan(0);
	}

}
