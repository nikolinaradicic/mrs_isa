package mrs.app.controller;

import java.util.Collection;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import mrs.app.DTOs.GuestDTO;
import mrs.app.DTOs.WorkingShiftDTO;
import mrs.app.domain.Bartender;
import mrs.app.domain.Bidder;
import mrs.app.domain.Chef;
import mrs.app.domain.Employee;
import mrs.app.domain.Guest;
import mrs.app.domain.RestaurantManager;
import mrs.app.domain.SystemManager;
import mrs.app.domain.User;
import mrs.app.domain.UserType;
import mrs.app.domain.Waiter;
import mrs.app.domain.restaurant.WorkingShift;
import mrs.app.security.JwtTokenUtil;
import mrs.app.service.UserService;
import mrs.app.service.WorkingShiftService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.mysql.jdbc.exceptions.jdbc4.MySQLIntegrityConstraintViolationException;

@RestController
public class UserController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;
	
	@Autowired
	private WorkingShiftService workingShiftService;
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@Value("${jwt.header}")
    private String tokenHeader;
	
	@Autowired
    private UserDetailsService userDetailsService;
	
	@RequestMapping(
			value = "/api/guestRegistration",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public ResponseEntity<User> registerGuest(@RequestBody Guest user) throws Exception {
		logger.info("> register guest");
		PasswordEncoder enc = new BCryptPasswordEncoder();
		String encoded = enc.encode(user.getPassword());
		user.setPassword(encoded);
		Guest savedUser = (Guest) userService.create(user);
		logger.info("< register guest");
		return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
	}
	
	@RequestMapping(
			value = "/api/sysManagerRegistration",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('SYSTEM_MANAGER')")
	public ResponseEntity<User> registerSysManager(
			@RequestBody SystemManager user) throws Exception {
		logger.info("> register sistem manager");
		try{
			PasswordEncoder enc = new BCryptPasswordEncoder();
			String encoded = enc.encode(user.getPassword());
			user.setPassword(encoded);
			SystemManager savedUser = (SystemManager) userService.create(user);
			logger.info("< register sistem manager");
			return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
		}
		catch(MySQLIntegrityConstraintViolationException e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(
			value = "/bidderRegistration",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<User> registerBidder(
			@RequestBody Bidder user) throws Exception {
		logger.info("> register bidder");
		try{
			PasswordEncoder enc = new BCryptPasswordEncoder();
			String encoded = enc.encode(user.getPassword());
			user.setPassword(encoded);
			Bidder savedUser = (Bidder) userService.create(user);
			logger.info("< register bidder");
			return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
		}
		catch(MySQLIntegrityConstraintViolationException e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(
			value = "/api/restManagerRegistration",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('SYSTEM_MANAGER')")
	public ResponseEntity<User> registerRestManager(
			@RequestBody RestaurantManager user) throws Exception {
		logger.info("> register restaurant manager");
		PasswordEncoder enc = new BCryptPasswordEncoder();
		String encoded = enc.encode(user.getPassword());
		user.setPassword(encoded);
		RestaurantManager savedUser = (RestaurantManager) userService.create(user);
		logger.info("< register restaurant manager");
		return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);

	}

	@RequestMapping(
			value = "/api/changePass",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> changePass(
			@RequestBody User user, HttpServletRequest request) throws Exception {
		logger.info("> change password");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User saved = userService.findByUsername(username);
        PasswordEncoder enc = new BCryptPasswordEncoder();
		String encoded = enc.encode(user.getPassword());
		saved.setPassword(encoded);
		User changedUser = (User) userService.change(saved);
		logger.info("< change password");
		return new ResponseEntity<User>(changedUser,HttpStatus.CREATED);
		
	}
	
	@RequestMapping(
			value = "/api/changePersonalData",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> changePersonalData(
			@RequestBody User user, HttpServletRequest request) throws Exception {
		logger.info("> change personal data");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User saved = userService.findByUsername(username);
		user.setId(saved.getId());
		user.setPassword(saved.getPassword());
		User changedUser = (User) userService.changeData(user);
		logger.info("< change personal data");
		return new ResponseEntity<User>(changedUser,HttpStatus.CREATED);
		
	}
	
	@RequestMapping(
			value = "/api/getUser",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser(HttpServletRequest request) {
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User saved = userService.findByUsername(username);
        return new ResponseEntity<User>(saved, HttpStatus.OK);
	}
	
/*	@RequestMapping(
			value = "/api/getUserRepresentation",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<GuestDTO> getUserRepresentation() {
		logger.info("> get user representation");
		User sessionUser = (User) httpSession.getAttribute("user");
		Guest user = (Guest) userService.getUser(sessionUser);
		GuestDTO retval = new GuestDTO(user);
		logger.info("<  get user representation");
		return new ResponseEntity<GuestDTO>(retval,
				HttpStatus.OK);
	}*/
	
	/*@RequestMapping(
			value = "/api/addFriend",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('GUEST')")
	public ResponseEntity<User> addFriend(
			@RequestBody Guest friend) throws Exception {
		logger.info("> add friends");
		//Guest current = (Guest) httpSession.getAttribute("user");
		logger.info("< add friends");
		if (userService.addFriend(current, friend)){
			return new ResponseEntity<User>(current,HttpStatus.CREATED);
		}
		else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	
	}
	*/
	/*@RequestMapping(
			value = "/api/acceptFriend",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('GUEST')")
	public ResponseEntity<User> acceptFriend(
			@RequestBody Guest friend) throws Exception {
		logger.info("> add friends");
		Guest current = (Guest) httpSession.getAttribute("user");
		logger.info("< add friends");
		if (current == null || current.getClass()!= Guest.class){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		if (userService.acceptFriend(current, friend)){
			return new ResponseEntity<User>(current,HttpStatus.CREATED);
		}
		else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	
	}
	
	@RequestMapping(
			value = "/api/unFriend",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('GUEST')")
	public ResponseEntity<User> unfriend(
			@RequestBody Guest friend) throws Exception {
		System.out.println(friend.toString());
		logger.info("> unfriend");
		Guest current = (Guest) httpSession.getAttribute("user");
		logger.info("< unfriend");
		if (current == null || current.getClass()!= Guest.class){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		if (userService.unfriend(current, friend)){
			return new ResponseEntity<User>(current,HttpStatus.CREATED);
		}
		else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	
	}

	@RequestMapping(
			value = "/getFriends",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Guest>> getFriends() throws Exception {
		logger.info("> add friends");
		Guest current = (Guest) httpSession.getAttribute("user");
		logger.info("< add friends");
		if (current == null || current.getClass()!= Guest.class){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		
		
		Collection<Guest> friends = userService.getFriends(current);
		return new ResponseEntity<Collection<Guest>>(friends, HttpStatus.OK);
	}
	*/
	
	@RequestMapping(
			value = "/registerBartender",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<Bartender> registerEmployee(
			@RequestBody Bartender user, HttpServletRequest request) throws Exception {
		logger.info("> register bartender");

		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        RestaurantManager saved = (RestaurantManager) userService.findByUsername(username);
		user.setRestaurant(saved.getRestaurant());
		
		PasswordEncoder enc = new BCryptPasswordEncoder();
		String encoded = enc.encode(user.getPassword());
		saved.setPassword(encoded);
		
		Bartender savedUser = (Bartender) userService.create(user);
		logger.info("< register Bartender");
		return new ResponseEntity<Bartender>(savedUser, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(
			value = "/registerWaiter",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<Waiter> registerWaiter(
			@RequestBody Waiter user, HttpServletRequest request) throws Exception {
		logger.info("> register waiter");
		
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        RestaurantManager saved = (RestaurantManager) userService.findByUsername(username);
		user.setRestaurant(saved.getRestaurant());
		
		PasswordEncoder enc = new BCryptPasswordEncoder();
		String encoded = enc.encode(user.getPassword());
		saved.setPassword(encoded);
		
		Waiter savedUser = (Waiter) userService.create(user);
		logger.info("< register waiter");
		return new ResponseEntity<Waiter>(savedUser, HttpStatus.CREATED);
	}
	
	
	@RequestMapping(
			value = "/registerChef",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<Chef> registerChef(@RequestBody Chef user, HttpServletRequest request) throws Exception {
		logger.info("> register chef");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        RestaurantManager saved = (RestaurantManager) userService.findByUsername(username);
		user.setRestaurant(saved.getRestaurant());
		
		PasswordEncoder enc = new BCryptPasswordEncoder();
		String encoded = enc.encode(user.getPassword());
		saved.setPassword(encoded);
		Chef savedUser = (Chef) userService.create(user);
		logger.info("< register chef");
		return new ResponseEntity<Chef>(savedUser, HttpStatus.CREATED);
		
	}
	
	
	@RequestMapping(
			value = "/getEmployees",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<Collection<Employee>> getEmployees(HttpServletRequest request){
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        RestaurantManager saved = (RestaurantManager) userService.findByUsername(username);
		Collection<Employee> employees = userService.findEmployees(saved.getRestaurant());
		return new ResponseEntity<Collection<Employee>>(employees, HttpStatus.OK);
	}

	@RequestMapping(
			value = "/addWorkingShift",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<WorkingShift> addWorkingShift(@RequestBody WorkingShift shift, HttpServletRequest request){
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        RestaurantManager saved = (RestaurantManager) userService.findByUsername(username);
		Employee employee = (Employee) userService.findEmployee(shift.getEmployee().getEmail());
		shift.setRestaurant(saved.getRestaurant());
		shift.setEmployee(employee);
		WorkingShift savedShift = workingShiftService.create(shift);
		return new ResponseEntity<WorkingShift>(savedShift, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/getWorkingShifts",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<WorkingShift>> getWorkingShifts(@RequestBody WorkingShiftDTO shift, HttpServletRequest request){
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        RestaurantManager current = (RestaurantManager) userService.findByUsername(username);
		Collection<WorkingShift> saved = workingShiftService.findForFilter(current.getRestaurant(), shift.getStart(), shift.getEnd());
		return new ResponseEntity<Collection<WorkingShift>>(saved, HttpStatus.OK);
	}
}