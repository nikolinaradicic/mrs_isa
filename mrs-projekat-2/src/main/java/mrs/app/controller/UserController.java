
package mrs.app.controller;

import java.util.ArrayList;
import java.util.Collection;

import javax.servlet.http.HttpSession;

import mrs.app.DTOs.GuestDTO;
import mrs.app.domain.Bidder;
import mrs.app.domain.Guest;
import mrs.app.domain.RestaurantManager;
import mrs.app.domain.SystemManager;
import mrs.app.domain.User;
import mrs.app.service.UserService;

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
public class UserController {

	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;
	
	@Autowired
	private HttpSession httpSession;
	
	
	@RequestMapping(
			value = "/api/guests",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<GuestDTO>> getUsers() {
		logger.info("> get guests");

		Collection<User> users = userService.findAll();
		ArrayList<GuestDTO> retval = new ArrayList<GuestDTO>();
		for (User u : users){
			if (u.getClass().equals(Guest.class)){
				Guest g = (Guest) u;
				retval.add(new GuestDTO(g));
			}
			
		}
		logger.info("< get guests");
		return new ResponseEntity<Collection<GuestDTO>>(retval,
				HttpStatus.OK);
	}
	
	
	@RequestMapping(
			value = "/api/login",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> login(@RequestBody User user) {
		logger.info("> login");
		System.out.println(user.getEmail());
		User foundUser = userService.login(user.getEmail(), user.getPassword());
		
		if (foundUser == null) {
			return new ResponseEntity<User>(HttpStatus.NOT_FOUND);
		}
		logger.info("< login");
		System.out.println(foundUser.getEmail());
		httpSession.setAttribute("user", foundUser);
		return new ResponseEntity<User>(foundUser, HttpStatus.OK);
	}
	
	
	@RequestMapping(
			value = "/api/guestRegistration",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	
	public ResponseEntity<User> registerGuest(
			@RequestBody Guest user) throws Exception {
		logger.info("> register guest");
		try{
			Guest savedUser = (Guest) userService.create(user);
			logger.info("< register guest");
			return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
		}
		catch(MySQLIntegrityConstraintViolationException e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}
	
	@RequestMapping(
			value = "/api/sysManagerRegistration",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> registerSysManager(
			@RequestBody SystemManager user) throws Exception {
		logger.info("> register sistem manager");
		User current = (User) httpSession.getAttribute("user");
		if (current == null || current.getClass() != SystemManager.class){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		try{
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
	
	public ResponseEntity<User> registerBidder(
			@RequestBody Bidder user) throws Exception {
		logger.info("> register bidder");
		User current = (User) httpSession.getAttribute("user");
		if (current == null || current.getClass() != RestaurantManager.class){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		try{
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
	public ResponseEntity<User> registerRestManager(
			@RequestBody RestaurantManager user) throws Exception {
		logger.info("> register restaurant manager");
		User current = (User) httpSession.getAttribute("user");
		if (current == null || current.getClass() != SystemManager.class){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		try{
			RestaurantManager savedUser = (RestaurantManager) userService.create(user);
			logger.info("< register restaurant manager");
			return new ResponseEntity<User>(savedUser, HttpStatus.CREATED);
		}
		catch(MySQLIntegrityConstraintViolationException e){
			e.printStackTrace();
		}
		return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	}

	@RequestMapping(
			value = "/api/changePass",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> changePass(
			@RequestBody User user) throws Exception {
		logger.info("> change password");
		User current = (User) httpSession.getAttribute("user");
		if (current == null){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		current.setPassword(user.getPassword());
		User changedUser = (User) userService.change(current);
		logger.info("< change password");
		return new ResponseEntity<User>(changedUser,HttpStatus.CREATED);
		
	}
	
	@RequestMapping(
			value = "/api/changePersonalData",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> changePersonalData(
			@RequestBody User user) throws Exception {
		logger.info("> change personal data");
		User current = (User) httpSession.getAttribute("user");
		if (current == null){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		user.setId(current.getId());
		user.setPassword(current.getPassword());
		user.setRepeatedPassword(current.getRepeatedPassword());
		User changedUser = (User) userService.changeData(user);
		logger.info("< change personal data");
		return new ResponseEntity<User>(changedUser,HttpStatus.CREATED);
		
	}
	
	@RequestMapping(
			value = "/api/getUser",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> getUser() {
		logger.info("> getUser");
		User user=(User)httpSession.getAttribute("user");
		logger.info("< getUser");
		return new ResponseEntity<User>(user,
				HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/api/addFriends",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<User> addFriends(
			@RequestBody Guest friend) throws Exception {
		logger.info("> add friends");
		Guest current = (Guest) httpSession.getAttribute("user");
		logger.info("< add friends");
		if (current == null || current.getClass()!= Guest.class){
			return new ResponseEntity<>(HttpStatus.UNAUTHORIZED);
		}
		if (userService.addFriend(current, friend)){
			return new ResponseEntity<User>(current,HttpStatus.CREATED);
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

}
