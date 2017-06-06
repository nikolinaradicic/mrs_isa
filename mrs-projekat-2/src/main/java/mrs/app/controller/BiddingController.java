package mrs.app.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import mrs.app.DTOs.GroceryListDTO;
import mrs.app.DTOs.OfferDTO;
import mrs.app.domain.Bidder;
import mrs.app.domain.RestaurantManager;
import mrs.app.domain.restaurant.GroceryList;
import mrs.app.domain.restaurant.Offer;
import mrs.app.security.JwtTokenUtil;
import mrs.app.service.GroceryListService;
import mrs.app.service.OfferService;
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
	
	@Autowired
	private OfferService offerService;
	
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
			value = "/getActiveLists",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('BIDDER')")
	public ResponseEntity<Collection<GroceryListDTO>> getActiveLists(HttpServletRequest request) throws Exception {
		logger.info("> get active grocery lists");
		Collection<GroceryList> saved = groceryListService.findAllActive(new Date());
		ArrayList<GroceryListDTO> retVal = new ArrayList<GroceryListDTO>();
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Bidder user = (Bidder) userService.findByUsername(username);
		for (GroceryList gl : saved){
			GroceryListDTO gld = new GroceryListDTO(gl.getId(),gl.getRestaurant(), null, gl.getEndDate(), gl.getText());
			Offer offer = offerService.findByListAndBidder(gl, user);
			gld.setOffer(offer);
			retVal.add(gld);
		}
		logger.info("< get active grocery lists");
		return new ResponseEntity<Collection<GroceryListDTO>>(retVal, HttpStatus.CREATED);
		
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
	
	@RequestMapping(
			value = "/getBids",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('BIDDER')")
	public ResponseEntity<Collection<OfferDTO>> getBids(HttpServletRequest request) throws Exception {
		logger.info("> get bids");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Bidder user = (Bidder) userService.findByUsername(username);
		Collection<Offer> saved = offerService.findForBidder(user);
		ArrayList<OfferDTO> retVal = new ArrayList<OfferDTO>();
		for (Offer o : saved){
			OfferDTO od = new OfferDTO(o.getGroceryList().getId(),o.getPrice(),o.getMessage());
			od.setRestaurant(o.getGroceryList().getRestaurant().getName());
			retVal.add(od);
		}
		logger.info("< get bids");
		return new ResponseEntity<Collection<OfferDTO>>(retVal, HttpStatus.OK);
		
	}
	
	@RequestMapping(
			value = "/getBid/{id}",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('BIDDER')")
	public ResponseEntity<Offer> getBid(@PathVariable Long id, HttpServletRequest request) throws Exception {
		logger.info("> get bid");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Bidder user = (Bidder) userService.findByUsername(username);
        GroceryList gl = groceryListService.findOne(id);
		Offer retVal = offerService.findByListAndBidder(gl, user);
		logger.info("< get bid");
		return new ResponseEntity<Offer>(retVal, HttpStatus.OK);
		
	}
	
	
	@RequestMapping(
			value = "/addBid",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('BIDDER')")
	public ResponseEntity<OfferDTO> addBid(@RequestBody OfferDTO offer, HttpServletRequest request) throws Exception {
		logger.info("> add bid");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Bidder user = (Bidder) userService.findByUsername(username);
        GroceryList gl = groceryListService.findOne(offer.getGroceryListId());
		Offer saved = new Offer(user,gl,offer.getPrice(),offer.getMessage(), false);
		offerService.create(saved);
		OfferDTO retVal = new OfferDTO(saved.getGroceryList().getId(), saved.getPrice(), saved.getMessage());
		logger.info("< add bid");
		return new ResponseEntity<OfferDTO>(retVal, HttpStatus.CREATED);
		
	}
	

}