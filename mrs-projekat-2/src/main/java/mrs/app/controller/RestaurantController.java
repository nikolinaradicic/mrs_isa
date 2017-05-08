package mrs.app.controller;

import java.util.Collection;
import javax.servlet.http.HttpSession;
import mrs.app.domain.RestaurantManager;
import mrs.app.domain.SystemManager;
import mrs.app.domain.User;
import mrs.app.domain.restaurant.Drink;
import mrs.app.domain.restaurant.Meal;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.Segment;
import mrs.app.service.RestaurantService;
import mrs.app.service.SegmentService;
import mrs.app.service.TableService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@RestController
public class RestaurantController {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private RestaurantService restaurantService;
	
	@Autowired
	private SegmentService segmentService;
	
	@Autowired
	private HttpSession httpSession;

	@RequestMapping(
			value = "/restaurants",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Restaurant>> getRestaurants() {
		logger.info("> get restaurants");
		
		Collection<Restaurant> restaurants = restaurantService.findAll();
		logger.info("< get restaurants");
		return new ResponseEntity<Collection<Restaurant>>(restaurants,HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/addrestaurant",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public ResponseEntity<Restaurant> addRestaurant(
			@RequestBody Restaurant restaurant) throws Exception {
		logger.info("> add restaurant");
		System.out.println(restaurant.getName());
		User currentUser = (User) httpSession.getAttribute("user");
		if (currentUser != null && currentUser.getClass().equals(SystemManager.class)){
			try{
				Restaurant savedRestaurant = restaurantService.create(restaurant);
				logger.info("< add restaurant");
				return new ResponseEntity<Restaurant>(savedRestaurant, HttpStatus.CREATED);
			}
			catch(MySQLIntegrityConstraintViolationException e){
				e.printStackTrace();
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
	}
	
	@RequestMapping(
			value = "/addDrink",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public ResponseEntity<Drink> addDrink(
			@RequestBody Drink drink) throws Exception {
		logger.info("> add drink");
		
		try{
			RestaurantManager currentUser = (RestaurantManager) httpSession.getAttribute("user");
			if (currentUser != null){
				drink.setRestaurant(currentUser.getRestaurant());
				Drink savedDrink = restaurantService.addDrink(drink);
				logger.info("< add drink");
				return new ResponseEntity<Drink>(savedDrink, HttpStatus.CREATED);
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch(MySQLIntegrityConstraintViolationException e){
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
	}
	
	@RequestMapping(
			value = "/addMeal",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public ResponseEntity<Meal> addMeal(
			@RequestBody Meal meal) throws Exception {
		logger.info("> add meal");
		
		try{
			RestaurantManager currentUser = (RestaurantManager) httpSession.getAttribute("user");
			if (currentUser != null){
				meal.setRestaurant(currentUser.getRestaurant());
				Meal savedMeal = restaurantService.addMeal(meal);
				logger.info("< add meal");
				return new ResponseEntity<Meal>(savedMeal, HttpStatus.CREATED);
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch(MySQLIntegrityConstraintViolationException e){
			e.printStackTrace();
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
	}
	
	@RequestMapping(
			value = "/changeInformation",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Restaurant> changeInformation(
			@RequestBody Restaurant restaurant) throws Exception {
		logger.info("> change information");
		
		try{
			RestaurantManager currentUser = (RestaurantManager) httpSession.getAttribute("user");
			if (currentUser != null){
				restaurant.setId(currentUser.getRestaurant().getId());
				int value = restaurantService.changeInformation(restaurant);
				System.out.println(value);
				logger.info("< change information");
				return new ResponseEntity<>(HttpStatus.CREATED);
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
	}
	
	@RequestMapping(
			value = "/addSegment",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	public ResponseEntity<Segment> addSegment(@RequestBody Segment segment){
		try{
			RestaurantManager currentUser = (RestaurantManager) httpSession.getAttribute("user");
			if (currentUser != null){
				segment.setRestaurant(currentUser.getRestaurant());
				Segment savedSegment = segmentService.create(segment);
				return new ResponseEntity<Segment>(savedSegment, HttpStatus.CREATED);
			}
		}
		catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
}
