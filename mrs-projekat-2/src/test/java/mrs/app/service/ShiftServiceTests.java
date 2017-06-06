package mrs.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Collection;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.Shift;
import mrs.app.repository.RestaurantRepository;
import mrs.app.repository.ShiftRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ShiftServiceTests {
	
	@Autowired
	ShiftService shiftService;
	
	@Autowired
	ShiftRepository shiftRepository;
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	Shift shift;
	
	Restaurant restaurant;
	
	@Before
	public void setUp(){
		restaurant = restaurantRepository.save(new Restaurant("naziv", "opis"));
		shift = shiftRepository.save(new Shift(restaurant, "prva", "08:00", "15:00"));
	}
	
	@After
	public void tearDown(){
		restaurantRepository.deleteAll();
		shiftRepository.deleteAll();
	}

	
	@Test
	public void createShiftTest()
	{
		boolean thrown = false;
		try {
			Shift s = shiftService.create(new Shift(restaurant, "neka", "01:03", "12:01"));
			assertThat(s.getName()).isEqualTo("neka"); 
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			thrown = true;
		}
		assertThat(thrown).isEqualTo(false);
	}
	
	@Test
	public void createShiftExceptionTest()
	{
		boolean thrown = false;
		try {
			Shift newShift = new Shift(restaurant, "neka","01:01", "02:02");
			newShift.setId(2L);
			shiftService.create(newShift);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			thrown = true;
		}
		assertThat(thrown).isEqualTo(true);
	}
	
	@Test
	public void findShiftsTest(){
		Collection<Shift> shifts = shiftService.findShifts(restaurant);
		assertThat(shifts.size()).isEqualTo(1);
	}
}
