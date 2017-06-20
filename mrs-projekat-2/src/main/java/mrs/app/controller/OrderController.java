package mrs.app.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import mrs.app.DTOs.ItemDrinkDTO;
import mrs.app.DTOs.ItemMealDTO;
import mrs.app.DTOs.MarkDTO;
import mrs.app.domain.Bartender;
import mrs.app.domain.Chef;
import mrs.app.domain.Guest;
import mrs.app.domain.User;
import mrs.app.domain.Waiter;
import mrs.app.domain.restaurant.BartenderDrink;
import mrs.app.domain.restaurant.Bill;
import mrs.app.domain.restaurant.ChefMeal;
import mrs.app.domain.restaurant.Drink;
import mrs.app.domain.restaurant.ItemDrink;
import mrs.app.domain.restaurant.ItemMeal;
import mrs.app.domain.restaurant.Mark;
import mrs.app.domain.restaurant.Meal;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.Visit;
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
		logger.info("> set order and visit");
		
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user= userService.findByUsername(username);
        if(user.getClass()==Waiter.class){
        	Waiter waiter= (Waiter)user;
            Restaurant restaurant= restaurantService.findOne(waiter.getRestaurant().getId());
            WaiterOrd defineOrder=orderService.setOrderMeal(order,restaurant,waiter);
            logger.info("< set order and visit");
    		return new ResponseEntity<WaiterOrd>(defineOrder,HttpStatus.OK);
        }
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
            for(Meal m: restaurant.getMenu()){
            	System.out.println(m.getName());
            }
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
			value="/getBartenderDrinks",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('BARTENDER') or hasRole('CHEF')")
	public ResponseEntity<Collection<WaiterOrd>> getBartenderDrinks(HttpServletRequest request
			){
		logger.info("> get all drinks bartender");
		
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user= userService.findByUsername(username);
        if(user.getClass()==Bartender.class){
        	Bartender bartender= (Bartender)user;
            Restaurant restaurant= restaurantService.findOne(bartender.getRestaurant().getId()); 
            Collection<WaiterOrd> defineOrder=orderService.getAllDrinks(restaurant);
    		logger.info("< get all drinks bartender");
    		return new ResponseEntity<Collection<WaiterOrd>>(defineOrder,HttpStatus.OK);
        }else if(user.getClass()==Chef.class){
        	Chef chef= (Chef)user;
            Restaurant restaurant= restaurantService.findOne(chef.getRestaurant().getId()); 
            Collection<WaiterOrd> defineOrder=orderService.getAllDrinks(restaurant);
    		logger.info("< get all drinks bartender");
    		return new ResponseEntity<Collection<WaiterOrd>>(defineOrder,HttpStatus.OK);
        }
        return new ResponseEntity<Collection<WaiterOrd>>(HttpStatus.NOT_FOUND);
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
        if(user.getClass()==Waiter.class){
        	Waiter waiter= (Waiter)user;
            Restaurant restaurant= restaurantService.findOne(waiter.getRestaurant().getId()); 
            BartenderDrink defineOrder=orderService.saveDrinks(order,restaurant);
            
    		logger.info("< set order chef");
    		return new ResponseEntity<BartenderDrink>(defineOrder,HttpStatus.OK);
        }
  
        return new ResponseEntity<BartenderDrink>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(
			value="/getMyOrders",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('WAITER')")
	public ResponseEntity<Collection<WaiterOrd>> saveBartenderDrink(HttpServletRequest request){
		logger.info("> get my orders");		
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user= userService.findByUsername(username);
        if(user.getClass()==Waiter.class){
        	Waiter waiter= (Waiter)user;
            Restaurant restaurant= restaurantService.findOne(waiter.getRestaurant().getId()); 
            Collection<WaiterOrd> defineOrder=orderService.getMyOrder(restaurant, waiter.getId());
            
    		logger.info("< get my orders");
    		return new ResponseEntity<Collection<WaiterOrd>>(defineOrder,HttpStatus.OK);
        }
  
        return new ResponseEntity<Collection<WaiterOrd>>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(
			value="/updateItemDrink",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes= MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('WAITER')")
	public ResponseEntity<ItemDrink> updateItemDrink(HttpServletRequest request,
			@RequestBody ItemDrink itemDr){
		logger.info("> update Item Drink");		
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user= userService.findByUsername(username);
        if(user.getClass()==Waiter.class){
            ItemDrink item=orderService.updateItemDrink(itemDr);
    		logger.info("< update Item Drink");
    		return new ResponseEntity<ItemDrink>(item,HttpStatus.OK);
        }
  
        return new ResponseEntity<ItemDrink>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(
			value="/updateItemMeal",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes= MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('WAITER')")
	public ResponseEntity<ItemMeal> updateItemMeal(HttpServletRequest request,
			@RequestBody ItemMeal itemMe){
		logger.info("> update Item Drink");		
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user= userService.findByUsername(username);
        if(user.getClass()==Waiter.class){
            ItemMeal item=orderService.updateItemMeal(itemMe);
    		logger.info("< update Item Drink");
    		return new ResponseEntity<ItemMeal>(item,HttpStatus.OK);
        }
  
        return new ResponseEntity<ItemMeal>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(
			value="/updateItemMealStatus",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes= MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('CHEF')")
	public ResponseEntity<ItemMeal> updateItemMealStatus(HttpServletRequest request,
			@RequestBody ItemMeal itemMe){
		logger.info("> update Item Meal Status");		
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user= userService.findByUsername(username);
        if(user.getClass()==Chef.class){
            ItemMeal item=orderService.updateItemMealStatus(itemMe);
    		logger.info("< update Item Meal Status");
    		return new ResponseEntity<ItemMeal>(item,HttpStatus.OK);
        }
  
        return new ResponseEntity<ItemMeal>(HttpStatus.NOT_FOUND);
	}
	
	
	@RequestMapping(
			value="/updateItemDrinkStatus",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes= MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('BARTENDER')")
	public ResponseEntity<ItemDrink> updateItemDrinkStatus(HttpServletRequest request,
			@RequestBody ItemDrink itemDr){
		logger.info("> update Item Drink Status");		
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user= userService.findByUsername(username);
        if(user.getClass()==Bartender.class){
            ItemDrink item=orderService.updateItemDrinkStatus(itemDr);
    		logger.info("< update Item Drink Status");
    		return new ResponseEntity<ItemDrink>(item,HttpStatus.OK);
        }
  
        return new ResponseEntity<ItemDrink>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(
			value="/deleteItemDrink",
			method = RequestMethod.POST,
			consumes= MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('WAITER')")
	public ResponseEntity<Integer> deleteItemDrink(HttpServletRequest request,
			@RequestBody ItemDrinkDTO orderDTO){
		logger.info("> delete Item Drink ");		
        orderService.deleteItemDrink(orderDTO);
        logger.info("< delete Item Drink");
        return new ResponseEntity<Integer>(1,HttpStatus.OK);
	}
	
	@RequestMapping(
			value="/deleteItemMeal",
			method = RequestMethod.POST,
			consumes= MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('WAITER')")
	public ResponseEntity<Integer> deleteItemMeal(HttpServletRequest request,
			@RequestBody ItemMealDTO orderDTO){
		logger.info("> delete Item Meal ");		
        orderService.deleteItemMeal(orderDTO);
        logger.info("< delete Item Meal");
        return new ResponseEntity<Integer>(1,HttpStatus.OK);
	}
	
	
	@RequestMapping(
			value="/defineVisit",
			method = RequestMethod.POST,
			consumes= MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('WAITER')")
	public ResponseEntity<Integer> defineVisit(HttpServletRequest request,
			@RequestBody Bill bill){
		logger.info("> define visit ");	
        Visit createdVisit=orderService.createVisit(bill);
        logger.info("< define visit");
        return new ResponseEntity<Integer>(1,HttpStatus.OK);
	}
	
	@RequestMapping(
			value="/getMyVisits",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('GUEST')")
	public ResponseEntity<Collection<Visit>> getMyVisits(HttpServletRequest request){
		logger.info("> get my visits");		
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user= userService.findByUsername(username);
        if(user.getClass()==Guest.class){
        	Guest guest= (Guest)user;
            System.err.println(guest.getId());
           	Collection<Visit> foundVisits= orderService.findMyVisits(guest); 
    		logger.info("< get my visits");
    		return new ResponseEntity<Collection<Visit>>(foundVisits,HttpStatus.OK);
        }
  
        return new ResponseEntity<Collection<Visit>>(HttpStatus.NOT_FOUND);
	}
	
	@RequestMapping(
			value="/rank",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes= MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('GUEST')")
	public ResponseEntity<Mark> rank(HttpServletRequest request,
			@RequestBody MarkDTO markDTO){
		logger.info("> rank");		
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User user= userService.findByUsername(username);
        if(user.getClass()==Guest.class){
        	Mark mark=orderService.mark(markDTO);
    		logger.info("< rank");
    		return new ResponseEntity<Mark>(mark,HttpStatus.OK);
        }
  
        return new ResponseEntity<Mark>(HttpStatus.NOT_FOUND);
	}
	
}
