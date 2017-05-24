package mrs.app.controller;

import java.util.Collection;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mrs.app.DTOs.TableDTO;
import mrs.app.domain.RestaurantManager;
import mrs.app.domain.User;
import mrs.app.domain.Waiter;
import mrs.app.domain.restaurant.Drink;
import mrs.app.domain.restaurant.Meal;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.RestaurantTable;
import mrs.app.domain.restaurant.Segment;
import mrs.app.security.JwtTokenUtil;
import mrs.app.service.RestaurantService;
import mrs.app.service.SegmentService;
import mrs.app.service.TableService;
import mrs.app.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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
    private JwtTokenUtil jwtTokenUtil;
	
	@Value("${jwt.header}")
    private String tokenHeader;
	
	@Autowired
    private UserService userService;

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
	@PreAuthorize("hasRole('SYSTEM_MANAGER')")
	public ResponseEntity<Restaurant> addRestaurant(
			@RequestBody Restaurant restaurant) throws Exception {
		logger.info("> add restaurant");
		System.out.println(restaurant.getName());
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
	
	@RequestMapping(
			value = "/changeInformation",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<Restaurant> changeInformation(
			@RequestBody Restaurant restaurant, HttpServletRequest request) throws Exception {
		logger.info("> change information");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        RestaurantManager saved = (RestaurantManager) userService.findByUsername(username);
		restaurant.setId(saved.getRestaurant().getId());
		restaurantService.changeInformation(restaurant);
		logger.info("< change information");
		return new ResponseEntity<Restaurant>(restaurant,HttpStatus.OK);
		
	}
	
	@RequestMapping(
			value = "/addDrink",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<Drink> addDrink(@RequestBody Drink drink) throws Exception {
		logger.info("> add drink");
		Drink savedDrink = restaurantService.addDrink(drink);
		logger.info("< add drink");
		return new ResponseEntity<Drink>(savedDrink, HttpStatus.CREATED);
	}
		
	@RequestMapping(
			value = "/addMeal",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<Meal> addMeal(@RequestBody Meal meal) throws Exception {
		logger.info("> add meal");
		Meal savedMeal = restaurantService.addMeal(meal);
		logger.info("< add meal");
		return new ResponseEntity<Meal>(savedMeal, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(
			value = "/addSegment",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE
			)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<Segment> addSegment(@RequestBody Segment segment, HttpServletRequest request) throws Exception{
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        RestaurantManager saved = (RestaurantManager) userService.findByUsername(username);
		segment.setRestaurant(saved.getRestaurant());
		Segment savedSegment = segmentService.create(segment);
		return new ResponseEntity<Segment>(savedSegment, HttpStatus.CREATED);
			
	}
	
	@RequestMapping(
			value="/saveChart/{id}",
			method = RequestMethod.POST)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<String> saveChart(@PathVariable String id, @RequestBody String chart, HttpServletRequest request){
		logger.info("> save chart");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        RestaurantManager saved = (RestaurantManager) userService.findByUsername(username);
		Segment savedSegment = segmentService.findSegment(id, saved.getRestaurant());
		segmentService.updateSegment(chart, savedSegment.getId());
		logger.info("< save chart");
		return new ResponseEntity<String>(savedSegment.getChart(), HttpStatus.CREATED);
	}
	
	@RequestMapping(
			value = "/getSegment",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER') or hasRole('WAITER')")
	public ResponseEntity<Segment> getSegment(@RequestBody String segmentName, HttpServletRequest request) throws Exception {
		logger.info("> get segment");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User saved =  userService.findByUsername(username);
        if(saved.getClass().equals(RestaurantManager.class)){
        	RestaurantManager manager=(RestaurantManager)saved;
        	Segment segment  = segmentService.findSegment(segmentName, manager.getRestaurant());
        	logger.info("< get segment");
    		return new ResponseEntity<Segment>(segment, HttpStatus.OK);
        }else{
        	Waiter waiter=(Waiter)saved;
        	Segment segment  = segmentService.findSegment(segmentName, waiter.getRestaurant());
        	logger.info("< get segment");
    		return new ResponseEntity<Segment>(segment, HttpStatus.OK);
        }
		
		
	}
	
	@RequestMapping(value="/saveTables/{id}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<List<TableDTO>> saveTables( @PathVariable String id, @RequestBody List<TableDTO> tables, HttpServletRequest request){
		logger.info("> save tables");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        RestaurantManager saved = (RestaurantManager) userService.findByUsername(username);
		Segment segment = segmentService.findSegment(id, saved.getRestaurant());		
		tableService.saveTables(tables, segment);
		logger.info("< save tables");
		return new ResponseEntity<>(HttpStatus.CREATED);
	}
	
	@RequestMapping(
			value="/getTable/{id}",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<RestaurantTable> getTable(@PathVariable String id, @RequestBody TableDTO table, HttpServletRequest request){
		logger.info("> get table");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        RestaurantManager saved = (RestaurantManager) userService.findByUsername(username);
		Segment segment = segmentService.findSegment(id, saved.getRestaurant());
		RestaurantTable savedTable = tableService.findByNameAndSegment(table.getName(), segment);
		logger.info("< get table");
		return new ResponseEntity<RestaurantTable>(savedTable, HttpStatus.OK);

	}
	@RequestMapping(
			value = "/sortRestaurants",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<List<Restaurant>> sortRestaurants() {
		logger.info("> sort restaurants");
		List<Restaurant> restaurants = restaurantService.findAllSort();
		logger.info("< get restaurants");
		return new ResponseEntity<List<Restaurant>>(restaurants,HttpStatus.OK);
	}
	
	@RequestMapping(
			value="/getDrinks",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER') or hasRole('GUEST')")
	public ResponseEntity<Collection<Drink>> getDrinkList(HttpServletRequest request,@RequestBody Restaurant r){
		logger.info("> get drink list");
        Restaurant restaurant= restaurantService.findOne(r.getId());
        
		logger.info("< get drink list");
		return new ResponseEntity<Collection<Drink>>(restaurant.getDrinkList(), HttpStatus.OK);

	}
	
	@RequestMapping(
			value="/getMeals",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER') or hasRole('GUEST')")
	public ResponseEntity<Collection<Meal>> getMealList(HttpServletRequest request,@RequestBody Restaurant r){
		logger.info("> get meal list");
        Restaurant restaurant= restaurantService.findOne(r.getId());     
		logger.info("< get meal list");
		return new ResponseEntity<Collection<Meal>>(restaurant.getMenu(), HttpStatus.OK);

	}
	
	
}
