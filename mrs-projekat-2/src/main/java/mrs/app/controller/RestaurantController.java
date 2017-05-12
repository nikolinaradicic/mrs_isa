package mrs.app.controller;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpSession;

import mrs.app.DTOs.TableDTO;
import mrs.app.domain.RestaurantManager;
import mrs.app.domain.SystemManager;
import mrs.app.domain.User;
import mrs.app.domain.restaurant.Drink;
import mrs.app.domain.restaurant.Meal;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.RestaurantTable;
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
import org.springframework.web.bind.annotation.PathVariable;
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
	private TableService tableService;
	
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
	
	
	@RequestMapping(
			value = "/getSegment",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Segment> getChart(@RequestBody String segmentName) throws Exception {
		logger.info("> get segment");
		
		try{
			RestaurantManager currentUser = (RestaurantManager) httpSession.getAttribute("user");
			if (currentUser != null){
				Segment segment  = segmentService.findSegment(segmentName, currentUser.getRestaurant());
				logger.info("< get segment");
				return new ResponseEntity<Segment>(segment, HttpStatus.CREATED);
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		
	}
	
	
	@RequestMapping(value="/saveTables/{id}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<TableDTO>> saveTables( @PathVariable String id, @RequestBody List<TableDTO> tables){
		logger.info("> save tables");
	
		try{
			RestaurantManager currentUser = (RestaurantManager) httpSession.getAttribute("user");
			if (currentUser != null){
				Segment segment = segmentService.findSegment(id, currentUser.getRestaurant());
				
				tableService.saveTables(tables, segment);
				logger.info("< save tables");
				return new ResponseEntity<>(HttpStatus.CREATED);
			}
		}
	catch(Exception e){
		e.printStackTrace();
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	
	@RequestMapping(
				value="/saveChart/{id}",
				method = RequestMethod.POST)
	public ResponseEntity<String> saveChart(@PathVariable String id, @RequestBody String chart){
		logger.info("> save chart");
		
		try{
			RestaurantManager currentUser = (RestaurantManager) httpSession.getAttribute("user");
			if (currentUser != null){
				
				Segment saved = segmentService.findSegment(id, currentUser.getRestaurant());
				segmentService.updateSegment(chart, saved.getId());
				logger.info("< save chart");
				return new ResponseEntity<String>(saved.getChart(), HttpStatus.CREATED);
			}
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	
	@RequestMapping(
			value="/getTable/{id}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<RestaurantTable> getTable(@PathVariable String id, @RequestBody TableDTO table){
		logger.info("> get table");
	
		try{
			RestaurantManager currentUser = (RestaurantManager) httpSession.getAttribute("user");
			if (currentUser != null){
				Segment saved = segmentService.findSegment(id, currentUser.getRestaurant());
				RestaurantTable savedTable = tableService.findByNameAndSegment(table.getName(), saved);
				System.out.println(savedTable.getChairNumber());
				System.out.println(savedTable.getName());
				System.out.println(savedTable.getId());
				logger.info("< get table");
			return new ResponseEntity<RestaurantTable>(savedTable, HttpStatus.OK);
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	catch(Exception e){
		e.printStackTrace();
	}
	
	return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
}
	
	
}
