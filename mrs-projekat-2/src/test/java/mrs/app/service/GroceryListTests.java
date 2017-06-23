package mrs.app.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collection;
import java.util.Date;

import mrs.app.domain.restaurant.GroceryList;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.repository.GroceryListRepository;
import mrs.app.repository.RestaurantRepository;
import mrs.app.repository.UserRepository;

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
public class GroceryListTests {
	
	@Autowired
	GroceryListService glService;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	
	@Autowired
	private GroceryListRepository glRepository;
	
	Restaurant rest;
	
	GroceryList gl;
	
	@Before
	public void setUp() throws Exception {
		rest = new Restaurant("neki", "opis");
		rest = restaurantRepository.save(rest);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date start = sdf.parse("16-06-2017");
		Date end = sdf.parse("27-06-2017");
		gl = glService.create(new GroceryList(rest, start, end, "daj svega"));
	}
	
	@After
	public void tearDown(){
		glRepository.delete(gl.getId());
		restaurantRepository.delete(rest.getId());
	}

	
	@Test
	public void createGlExceptionTest()
	{
		boolean thrown = false;
		try {
			GroceryList newGl = new GroceryList();
			newGl.setId(2L);
			glService.create(newGl);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			thrown = true;
		}
		assertThat(thrown).isEqualTo(true);
	}
	
	@Test
	public void findByRestaurantTest()
	{
		Collection<GroceryList> found = glService.findByRestaurant(rest, new Date());
		
		assertThat(found.size()).isGreaterThan(0);
	}
	
	
	
}
