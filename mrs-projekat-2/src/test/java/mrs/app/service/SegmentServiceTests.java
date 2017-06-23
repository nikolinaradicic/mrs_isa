package mrs.app.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.Segment;
import mrs.app.repository.RestaurantRepository;
import mrs.app.repository.SegmentRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value = "test")
public class SegmentServiceTests {
	
	@Autowired
	SegmentService segmentService;
	
	@Autowired
	SegmentRepository segmentRepository;
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	Segment segment;
	Restaurant restaurant;
	
	@Before
	public void setUp(){
		restaurant = restaurantRepository.save(new Restaurant("naziv", "opis"));
		segment = segmentRepository.save(new Segment("basta", restaurant));
	}
	
	@After
	public void tearDown(){
		restaurantRepository.delete(restaurant.getId());
	}
	
	@Test
	public void createSegmentTest()
	{
		boolean thrown = false;
		try {
			Segment s = segmentService.create(new Segment("novi segment", restaurant));
			assertThat(s.getName()).isEqualTo("novi segment");
			Collection<Segment> segments = restaurantRepository.findOne(restaurant.getId()).getSegments();
			assertThat(segments).isNotNull();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			thrown = true;
		}
		assertThat(thrown).isEqualTo(false);
	}
	
	@Test
	public void createSegmentExceptionTest()
	{
		boolean thrown = false;
		try {
			Segment newSegment = new Segment("novi segment", restaurant);
			newSegment.setId(2L);
			segmentService.create(newSegment);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			thrown = true;
		}
		assertThat(thrown).isEqualTo(true);
	}
	
	@Test
	public void findSegmentTest()
	{
		Segment foundSegment = segmentService.findSegment(segment.getName(), restaurant);
		assertThat(foundSegment).isEqualTo(segment);
	}
	
	@Test
	public void findForRestaurantTest()
	{
		Collection<Segment> found = segmentService.findForRestaurant(restaurant);
		assertThat(found.size()).isGreaterThan(0);
	}
	
	

}
