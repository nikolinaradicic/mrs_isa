package mrs.app.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.Segment;
import mrs.app.repository.RestaurantRepository;
import mrs.app.repository.SegmentRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class SegmentRepositoryTest {
	@Autowired
	SegmentRepository segmentRepository;
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Test
	public void executesQueryMethodsCorrectly()
	{
		Restaurant restaurant = new Restaurant("restoran", "opis");
		Restaurant saved = restaurantRepository.save(restaurant);
		Segment segment = new Segment();
		segment.setName("ime");
		segment.setRestaurant(saved);
		segmentRepository.save(segment);
		List<Segment> all = segmentRepository.findAll();
		
		saved = restaurantRepository.findOne(saved.getId());
		assertThat(all.size()).isGreaterThan(0);
	}

}
