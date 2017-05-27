package mrs.app.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import mrs.app.domain.restaurant.Drink;
import mrs.app.domain.restaurant.Meal;
import mrs.app.domain.restaurant.Order;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.security.JwtTokenUtil;
import mrs.app.service.OrderService;
import mrs.app.service.RestaurantService;

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

public class OrderController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@Autowired
	private OrderService orderService;
	
	@Autowired
	private RestaurantService restaurantService;
	
	
	@Value("${jwt.header}")
    private String tokenHeader;
	
	@RequestMapping(
			value="/setOrder",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('WAITER')")
	public ResponseEntity<Order> setOrder(HttpServletRequest request,
			@RequestBody Restaurant r, @RequestBody Collection<Drink> drink_list,
			@RequestBody Collection<Meal> meal_list){
		logger.info("> set order");
        Restaurant restaurant= restaurantService.findOne(r.getId());  
        Order order=orderService.setOrder(meal_list, drink_list, r);
		logger.info("< set order");
		return new ResponseEntity<Order>(order, HttpStatus.OK);

	}
	
}
