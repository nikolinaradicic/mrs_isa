package mrs.app.repository;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import mrs.app.domain.Waiter;
import mrs.app.domain.restaurant.Drink;
import mrs.app.domain.restaurant.Meal;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.WaiterOrd;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;




@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderRepositoryTest {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Test
	public void executesQueryMethodsCorrectly(){
		
		Restaurant restaurant = new Restaurant("restoran", "opis");
		Restaurant saved = restaurantRepository.save(restaurant);
		Meal meal = new Meal("jelo", "opis", 200, saved);
		Drink drink= new Drink("pice", "pice", 30, saved);
		WaiterOrd order= new WaiterOrd();
		order.getDrinks().add(drink);
		order.getMeals().add(meal);
		orderRepository.save(order);
		
		List<WaiterOrd> all= orderRepository.findAll();
		assertThat(all.size()).isGreaterThan(0);
	}
	
}
