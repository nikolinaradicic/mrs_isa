package mrs.app.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.Shift;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiftRepositoryTest {
	
	@Autowired
	ShiftRepository shiftRepository;
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Test
	public void executesQueryMethodsCorrectly()
	{
		Restaurant restaurant = new Restaurant("restoran", "opis");
		restaurantRepository.save(restaurant);
		Shift shift = new Shift(restaurant,"prva smjena", "8:00", "15:00");
		shiftRepository.save(shift);
		
		Collection<Shift> found = shiftRepository.findByRestaurant(restaurant);
		assertThat(found.size()).isEqualTo(1);
	}

}
