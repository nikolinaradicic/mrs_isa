package mrs.app.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import mrs.app.DTOs.GuestDTO;
import mrs.app.domain.Bartender;
import mrs.app.domain.Bidder;
import mrs.app.domain.Chef;
import mrs.app.domain.Employee;
import mrs.app.domain.Guest;
import mrs.app.domain.Notification;
import mrs.app.domain.RestaurantManager;
import mrs.app.domain.SystemManager;
import mrs.app.domain.User;
import mrs.app.domain.UserType;
import mrs.app.domain.Waiter;
import mrs.app.domain.restaurant.WorkingShift;
import mrs.app.security.JwtTokenUtil;
import mrs.app.service.NotificationService;
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
import org.springframework.web.bind.annotation.PathVariable;
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
	private NotificationService notificationService;
	
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
	
	@RequestMapping(
			value = "/api/getFriends",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('GUEST')")
	public ResponseEntity<Collection<GuestDTO>> getFriends(HttpServletRequest request) {
		logger.info("> get user representation");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Guest saved = (Guest) userService.findByUsername(username);
		ArrayList<GuestDTO> friendList=new ArrayList<GuestDTO>();
		for(Guest g:saved.getFriends()){
			GuestDTO guest=new GuestDTO(g);
			friendList.add(guest);
			
			
		}
		logger.info("<  get user representation");
		return new ResponseEntity<Collection<GuestDTO>>(friendList,
				HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/api/getUserRepresentation",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('GUEST')")
	public ResponseEntity<GuestDTO> getUserRepresentation(HttpServletRequest request) {
		logger.info("> get user representation");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Guest saved = (Guest) userService.findByUsername(username);
		GuestDTO dto = new GuestDTO(saved);
		logger.info("<  get user representation");
		return new ResponseEntity<GuestDTO>(dto,
				HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/getGuests",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('GUEST')")
	public ResponseEntity<Collection<GuestDTO>> getGuests(
			@RequestBody String name, HttpServletRequest request) throws Exception {
		logger.info("> getGuests");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Guest saved = (Guest) userService.findByUsername(username);
        Collection<Guest> guests=userService.getGuests(UserType.ROLE_GUEST, "%"+name+"%");
        ArrayList<GuestDTO> retVal=new ArrayList<GuestDTO>();
        for(Guest g: guests){
        	if(!saved.getFriends().contains(g)){
        		retVal.add(new GuestDTO(g));
        	}
        }
		logger.info("< getGuests");
		return new ResponseEntity<Collection<GuestDTO>>(retVal,HttpStatus.OK);
		
	}
	@RequestMapping(
			value = "/sortFriendsName",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('GUEST')")
	public ResponseEntity<List<GuestDTO>> sortFriends(HttpServletRequest request) {
		logger.info("> sort Friends name");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Guest saved = (Guest) userService.findByUsername(username);
        ArrayList<GuestDTO> retVal=new ArrayList<>();
        Collections.sort(saved.getFriends(), new Comparator<Guest>(){

			@Override
			public int compare(Guest g1, Guest g2) {
				// TODO Auto-generated method stub
				return g1.getName().compareTo(g2.getName());
			}
				
			
        	
        });
        for(Guest g:saved.getFriends()){
        	retVal.add(new GuestDTO(g));
        }
		logger.info("< sort friends name");
		return new ResponseEntity<List<GuestDTO>>(retVal,
				HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/sortFriendsLastName",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('GUEST')")
	public ResponseEntity<List<GuestDTO>> sortFriendsLastName(HttpServletRequest request) {
		logger.info("> sort Friends lastname");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Guest saved = (Guest) userService.findByUsername(username);
        ArrayList<GuestDTO> retVal=new ArrayList<>();
        Collections.sort(saved.getFriends(), new Comparator<Guest>(){

			@Override
			public int compare(Guest g1, Guest g2) {
				// TODO Auto-generated method stub
				return g1.getLastname().compareTo(g2.getLastname());
			}
				
			
        	
        });
        for(Guest g:saved.getFriends()){
        	retVal.add(new GuestDTO(g));
        }
		logger.info("< sort friends lastname");
		return new ResponseEntity<List<GuestDTO>>(retVal,
				HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/api/addFriend",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('GUEST')")
	public ResponseEntity<GuestDTO> addFriend(
			@RequestBody GuestDTO friend, HttpServletRequest request) throws Exception {
		logger.info("> add friends");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Guest saved = (Guest) userService.findByUsername(username);
		logger.info("< add friends");
		if (userService.addFriend(saved, friend)){
			return new ResponseEntity<GuestDTO>(friend,HttpStatus.CREATED);
		}
		else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	
	}
	
	@RequestMapping(
			value = "/api/acceptFriend",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('GUEST')")
	public ResponseEntity<User> acceptFriend(
			@RequestBody Guest friend, HttpServletRequest request) throws Exception {
		logger.info("> add friends");

		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Guest saved = (Guest) userService.findByUsername(username);
		logger.info("< add friends");
		if (userService.acceptFriend(saved, friend)){
			return new ResponseEntity<User>(saved,HttpStatus.CREATED);
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
			@RequestBody Guest friend,HttpServletRequest request) throws Exception {
		System.out.println(friend.toString());
		logger.info("> unfriend");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Guest saved = (Guest) userService.findByUsername(username);
		logger.info("< unfriend");

		if (userService.unfriend(saved, friend)){
			return new ResponseEntity<User>(saved,HttpStatus.OK);
		}
		else{
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}	
	}

	
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
		user.setPassword(encoded);
		
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
		user.setPassword(encoded);
		
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
		user.setPassword(encoded);
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
			value = "/getWorkingShiftsForEmployee",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<WorkingShift>> getWorkingShiftsWaiter(HttpServletRequest request){
		logger.info("> get shifts for employee");
		String token=request.getHeader(tokenHeader);
		String username=jwtTokenUtil.getUsernameFromToken(token);
		Employee current=(Employee)userService.findByUsername(username);
		try{
			if(current != null){
				
				Collection<WorkingShift> saved = workingShiftService.findShiftsForEmployee(current.getRestaurant());
				Collection<WorkingShift> saved1 = workingShiftService.findShiftsForEmployee(current.getRestaurant());
				if(current.getClass().equals(Waiter.class)){
					for(WorkingShift ws:saved){
						if(!ws.getEmployee().getClass().equals(Waiter.class)){	
							saved1.remove(ws);
						}
					}
				}else if(current.getClass().equals(Bartender.class)){
					for(WorkingShift ws:saved){
						if(!ws.getEmployee().getClass().equals(Bartender.class)){	
							saved1.remove(ws);
						}
					}	
				}else if(current.getClass().equals(Chef.class)){
					for(WorkingShift ws:saved){
						if(!ws.getEmployee().getClass().equals(Chef.class)){	
							saved1.remove(ws);
						}
					}
				}else{
					return new ResponseEntity<>(HttpStatus.NOT_FOUND);
				}
				logger.info("< get shifts for certain employee");
				return new ResponseEntity<Collection<WorkingShift>>(saved1, HttpStatus.OK);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
	}
	
	@RequestMapping(
			value = "/api/getNotifications",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<Notification>> getNotifications(HttpServletRequest request) {
		logger.info("> get notifications");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User saved = userService.findByUsername(username);
        Collection<Notification> retVal = notificationService.findByUser(saved);
		logger.info("<  get notifications");
		return new ResponseEntity<Collection<Notification>>(retVal,HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/api/updateNotification/{id}",
			method = RequestMethod.POST,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Notification> updateNotification(@PathVariable Long id, HttpServletRequest request) {
		logger.info("> update notifications");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        User saved = userService.findByUsername(username);
        Notification retVal = notificationService.updateNotification(id, saved);
		logger.info("<  update notification");
		return new ResponseEntity<Notification>(retVal,HttpStatus.OK);
	}
	
	
	
}