package mrs.app.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import mrs.app.domain.RestaurantManager;
import mrs.app.domain.restaurant.Shift;
import mrs.app.security.JwtTokenUtil;
import mrs.app.service.ShiftService;
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
public class ShiftController {


	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ShiftService shiftSetvice;
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@Value("${jwt.header}")
    private String tokenHeader;
	
	@Autowired
    private UserService userService;
	
	@RequestMapping(
			value = "/getShifts",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<Collection<Shift>> getShifts(HttpServletRequest request) {
		logger.info("> get shifts");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        RestaurantManager saved = (RestaurantManager) userService.findByUsername(username);
		Collection<Shift> shifts = shiftSetvice.findShifts(saved.getRestaurant());
		logger.info("< get shifts");
		return new ResponseEntity<Collection<Shift>>(shifts,HttpStatus.OK);
	}
	
	
	@RequestMapping(
			value = "/addShift",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<Shift> addShift(@RequestBody Shift shift, HttpServletRequest request) throws Exception {
		logger.info("> add shift");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        RestaurantManager saved = (RestaurantManager) userService.findByUsername(username);
        shift.setRestaurant(saved.getRestaurant());
		Shift savedShift = shiftSetvice.create(shift);
		logger.info("< add shift");
		return new ResponseEntity<Shift>(savedShift,HttpStatus.OK);
	}

}
