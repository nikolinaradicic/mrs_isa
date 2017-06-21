package mrs.app.repository;

import static org.assertj.core.api.Assertions.assertThat;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.repository.RestaurantRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantRepositoryTest {
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	Restaurant restaurant;
	
	@Before
	public void SetUp(){
		restaurant = restaurantRepository.save(new Restaurant("restoran", "opis"));
	}
	
	@Test
	public void updateRestaurantTest()
	{	
		restaurantRepository.updateRestaurant("novo ime", "novi opis", restaurant.getId());
		
		Restaurant updated = restaurantRepository.findOne(restaurant.getId());
		assertThat(updated.getDescription()).isEqualTo("novi opis");
		assertThat(updated.getName()).isEqualTo("novo ime");
	}

}
