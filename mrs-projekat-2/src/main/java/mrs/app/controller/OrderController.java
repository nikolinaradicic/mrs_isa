package mrs.app.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import mrs.app.domain.Chef;
import mrs.app.domain.Employee;
import mrs.app.domain.User;
import mrs.app.domain.Waiter;
import mrs.app.domain.restaurant.BartenderDrink;
import mrs.app.domain.restaurant.ChefMeal;
import mrs.app.domain.restaurant.Drink;
import mrs.app.domain.restaurant.Meal;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.WaiterOrd;
import mrs.app.security.JwtTokenUtil;
import mrs.app.service.OrderService;
import mrs.app.service.RestaurantService;
import mrs.app.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private RestaurantService restaurantService;
	
	
	@Value("${jwt.header}")
    private String tokenHeader;
	
	@RequestMapping(
			value="/setMealss",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('WAITER')")
	public ResponseEntity<WaiterOrd> setOrder(HttpServletRequest request,
			@RequestBody WaiterOrd order){
		logger.info("> set order");
		
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user= userService.findByUsername(username);
        if(user.getClass()==Waiter.class){
        	Waiter waiter= (Waiter)user;
            Restaurant restaurant= restaurantService.findOne(waiter.getRestaurant().getId());
            WaiterOrd defineOrder=orderService.setOrderMeal(order,restaurant);
            logger.info("< set order");
    		return new ResponseEntity<WaiterOrd>(defineOrder,HttpStatus.OK);
        }

//        Order order=orderService.setOrder(meal_list, drink_list, r);
        return new ResponseEntity<WaiterOrd>(HttpStatus.NOT_FOUND);
		

	}
	
	
	@RequestMapping(
			value="/getDrinks1",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('WAITER')")
	public ResponseEntity<Collection<Drink>> getDrinkList1(HttpServletRequest request){
		logger.info("> get drink list1");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user= userService.findByUsername(username);
        if(user.getClass()==Waiter.class){
        	Waiter waiter= (Waiter)user;
            Restaurant restaurant= restaurantService.findOne(waiter.getRestaurant().getId()); 
            logger.info("< get drink list1");
    		return new ResponseEntity<Collection<Drink>>(restaurant.getDrinkList(), HttpStatus.OK);
        }return new ResponseEntity<Collection<Drink>>(HttpStatus.NO_CONTENT);

	}
	
	@RequestMapping(
			value="/getMeals1",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('WAITER')")
	public ResponseEntity<Collection<Meal>> getMealList1(HttpServletRequest request){
		logger.info("> get meal list1");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user= userService.findByUsername(username);
        if(user.getClass()==Waiter.class){
        	Waiter waiter= (Waiter)user;
            Restaurant restaurant= restaurantService.findOne(waiter.getRestaurant().getId()); 
            logger.info("< get meal list1");
    		return new ResponseEntity<Collection<Meal>>(restaurant.getMenu(), HttpStatus.OK);
        }
        return new ResponseEntity<Collection<Meal>>(HttpStatus.NO_CONTENT);
      
	}
	
	@RequestMapping(
			value="/saveChefMeals",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('WAITER')")
	public ResponseEntity<ChefMeal> saveChefMeals(HttpServletRequest request,
			@RequestBody ChefMeal order){
		logger.info("> set order chef");
		
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user= userService.findByUsername(username);
        if(user.getClass()==Waiter.class){
        	Waiter waiter= (Waiter)user;
            Restaurant restaurant= restaurantService.findOne(waiter.getRestaurant().getId()); 
            ChefMeal defineOrder=orderService.getOrderChef(order,restaurant);     
            
    		logger.info("< set order chef");
    		return new ResponseEntity<ChefMeal>(defineOrder,HttpStatus.OK);

        }
        return new ResponseEntity<ChefMeal>(HttpStatus.NOT_FOUND);

	}
	
	@RequestMapping(
			value="/getChefMeals",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('CHEF')")
	public ResponseEntity<Collection<ChefMeal>> getChefMeals(HttpServletRequest request
			){
		logger.info("> set order chef");
		
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user= userService.findByUsername(username);
        if(user.getClass()==Chef.class){
        	Employee chef= (Employee)user;
            Restaurant restaurant= restaurantService.findOne(chef.getRestaurant().getId());
            Collection<ChefMeal> defineOrder=orderService.getAllMeals(restaurant);
            
    		logger.info("< set order chef");
    		return new ResponseEntity<Collection<ChefMeal>>(defineOrder,HttpStatus.OK);

        }
        return new ResponseEntity<Collection<ChefMeal>>(HttpStatus.NOT_FOUND);

	}
	
	
	@RequestMapping(
			value="/getBartenderDrinks",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('BARTENDER')")
	public ResponseEntity<Collection<BartenderDrink>> getBartenderDrinks(HttpServletRequest request
			){
		logger.info("> set order chef");
		
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user= userService.findByUsername(username);
//        if(user.getClass()==Waiter.class){
//        	Waiter waiter= (Waiter)user;
//            Restaurant restaurant= restaurantService.findOne(waiter.getRestaurant().getId()); 
//        }
        Collection<BartenderDrink> defineOrder=orderService.getAllDrinks();
        
		logger.info("< set order chef");
		return new ResponseEntity<Collection<BartenderDrink>>(defineOrder,HttpStatus.OK);

	}
	
	@RequestMapping(
			value="/saveBartenderDrinks",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('WAITER')")
	public ResponseEntity<BartenderDrink> saveBartenderDrink(HttpServletRequest request,
			@RequestBody BartenderDrink order){
		logger.info("> set order chef");
		
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user= userService.findByUsername(username);
//        if(user.getClass()==Waiter.class){
//        	Waiter waiter= (Waiter)user;
//            Restaurant restaurant= restaurantService.findOne(waiter.getRestaurant().getId()); 
//        }
        BartenderDrink defineOrder=orderService.saveDrinks(order);
        
		logger.info("< set order chef");
		return new ResponseEntity<BartenderDrink>(defineOrder,HttpStatus.OK);

	}
	
	
}
