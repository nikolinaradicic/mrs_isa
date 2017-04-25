package mrs.app.controller;

import java.util.Collection;

import javax.servlet.http.HttpSession;
import mrs.app.domain.Restaurant;
import mrs.app.domain.SystemManager;
import mrs.app.domain.User;
import mrs.app.service.RestaurantService;

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
	
	
}
