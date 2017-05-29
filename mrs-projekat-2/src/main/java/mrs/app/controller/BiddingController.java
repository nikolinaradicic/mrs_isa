package mrs.app.controller;

import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import mrs.app.domain.RestaurantManager;
import mrs.app.domain.restaurant.GroceryList;
import mrs.app.security.JwtTokenUtil;
import mrs.app.service.GroceryListService;
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


@RestController
public class BiddingController {
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@Value("${jwt.header}")
    private String tokenHeader;
	
	@Autowired
    private UserService userService;
	
	@Autowired
	private GroceryListService groceryListService;
	
	@RequestMapping(
			value = "/addList",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<GroceryList> addGroceryList(@RequestBody GroceryList gl, HttpServletRequest request) throws Exception {
		logger.info("> add grocery list");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        RestaurantManager user = (RestaurantManager) userService.findByUsername(username);
		gl.setRestaurant(user.getRestaurant());
		gl.setManager(user);
		GroceryList saved = groceryListService.create(gl);
		logger.info("< add grocery list");
		return new ResponseEntity<GroceryList>(saved, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(
			value = "/getLists",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<Collection<GroceryList>> getGroceryLists(HttpServletRequest request) throws Exception {
		logger.info("> get grocery lists");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        RestaurantManager user = (RestaurantManager) userService.findByUsername(username);
		Collection<GroceryList> saved = groceryListService.findByManager(user, new Date());
		logger.info("< get grocery lists");
		return new ResponseEntity<Collection<GroceryList>>(saved, HttpStatus.CREATED);
		
	}
	
	@RequestMapping(
			value = "/getSelectedList/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<GroceryList> getSelectedList( @PathVariable Long id, HttpServletRequest request) throws Exception {
		logger.info("> get grocery lists");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        RestaurantManager user = (RestaurantManager) userService.findByUsername(username);
		GroceryList saved = groceryListService.findOne(id);
		logger.info("< get grocery lists");
		if(saved.getRestaurant().equals(user.getRestaurant())){

			return new ResponseEntity<GroceryList>(saved, HttpStatus.OK);
		}
		return new ResponseEntity<GroceryList>(HttpStatus.FORBIDDEN);
	}

}
