package mrs.app.service;

import java.util.Collection;

import mrs.app.DTOs.GuestDTO;
import mrs.app.domain.Employee;
import mrs.app.domain.Guest;
import mrs.app.domain.User;
import mrs.app.domain.UserType;
import mrs.app.domain.restaurant.Restaurant;

public interface UserService {
	
	User findEmployee(String email);
	
	User login(String username, String password);
	
	Collection<User> findAll();

	User create(User user) throws Exception;
	
	User change(User user);
	
	User changeData(User user);
	
	boolean addFriend(Guest user, GuestDTO friend);

	boolean acceptFriend(Guest current, Guest friend);

	Collection<Guest> getFriends(Guest user);
	
	User getUser(User user);
	
	boolean unfriend(Guest current, Guest friend);
	
	Collection<Employee> findEmployees(Restaurant restaurant);

	User findByUsername(String username);
	
	Collection<Guest> getGuests(UserType type, String email);
}
