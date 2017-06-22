package mrs.app.service.implementation;

import java.util.Collection;

import mrs.app.DTOs.GuestDTO;
import mrs.app.domain.Bidder;
import mrs.app.domain.Employee;
import mrs.app.domain.Guest;
import mrs.app.domain.User;
import mrs.app.domain.UserType;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.repository.EmployeeRepository;
import mrs.app.repository.RestaurantRepository;
import mrs.app.repository.UserRepository;
import mrs.app.service.UserService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private EmployeeRepository employeeRepository;
    
    @Autowired
    private RestaurantRepository restaurantRepository;

	@Override
	public Collection<User> findAll() {
		// TODO Auto-generated method stub
		logger.info("> findAll");
        Collection<User> users = userRepository.findAll();
        logger.info("< findAll");
        return users;
	}


	@Override
	public User create(User user) throws Exception {
		// TODO Auto-generated method stub
		logger.info("> create");
        if (user.getId() != null) {
            logger.error(
                    "Pokusaj kreiranja novog entiteta, ali Id nije null.");
            throw new Exception(
                    "Id mora biti null prilikom perzistencije novog entiteta.");
        }
        
        User savedUser = userRepository.save(user);
        logger.info("< create");
        return savedUser;
	}

	@Override
	public User login(String email, String password) {
		return userRepository.findByEmailAndPassword(email, password);
		
	}


	@Override
	public User change(User user) {
		User savedUser = userRepository.findOne(user.getId());
		savedUser.setPassword(user.getPassword());
		if(savedUser instanceof Employee){
			Employee save = (Employee) user;
			save.setEnabled(true);
			return userRepository.save(save);
		}
		if(savedUser instanceof Bidder){
			Bidder save = (Bidder) user;
			save.setEnabled(true);
			return userRepository.save(save);
		}
		return userRepository.save(savedUser);
	}
	
	@Override
	public User changeData(User user) {
		User savedUser = userRepository.findOne(user.getId());
		savedUser.setEmail(user.getEmail());
		savedUser.setName(user.getName());
		savedUser.setLastname(user.getLastname());
		return userRepository.save(savedUser);
	}
	
	
	@Override
	public User changeDataEmployee(Employee user) {
		Employee savedUser = employeeRepository.findOne(user.getId());
		savedUser.setEmail(user.getEmail());
		savedUser.setName(user.getName());
		savedUser.setLastname(user.getLastname());
		savedUser.setBirthday(user.getBirthday());
		savedUser.setShoeSize(user.getShoeSize());
		savedUser.setUniformSize(user.getUniformSize());
		return userRepository.save(savedUser);
	}
	
	@Override
	public boolean addFriend(Guest user, GuestDTO friend) {
		// TODO Auto-generated method stub
		//Guest currentUser = (Guest) userRepository.findOne(user.getId());
		System.out.println(friend.getEmail());
		Guest requestedFriend = (Guest) userRepository.findByEmail(friend.getEmail());
		
		if(requestedFriend != null){
			requestedFriend.getRequests().add(user);
			
			userRepository.save(requestedFriend);
			return true;
		}
		return false;
	}


	@Override
	public boolean acceptFriend(Guest current, Guest friend) {
		// TODO Auto-generated method stub
		//Guest currentUser = (Guest) userRepository.findOne(current.getId());
		
		Guest requestedFriend = (Guest) userRepository.findByEmail(friend.getEmail());
		if (requestedFriend != null)
		{
			requestedFriend.getFriends().add(current);
			current.getFriends().add(requestedFriend);
			current.getRequests().remove(requestedFriend);
			userRepository.save(current);
			userRepository.save(requestedFriend);
			return true;
		}
		return false;
	}


	@Override
	public Collection<Guest> getFriends(Guest user) {
		// TODO Auto-generated method stub
		Guest savedGuest = userRepository.findByEmail(user.getEmail());
		if (savedGuest != null){
			return savedGuest.getFriends();
		}
		return null;
	}


	@Override
	public User getUser(User user) {
		// TODO Auto-generated method stub
		User savedUser = userRepository.findOne(user.getId());
		return savedUser;
	}


	@Override
	public boolean unfriend(Guest current, Guest friend) {
		// TODO Auto-generated method stub		
		Guest toUnfriend = (Guest) userRepository.findByEmail(friend.getEmail());
		if (toUnfriend != null)
		{
			toUnfriend.getFriends().remove(current);
			current.getFriends().remove(toUnfriend);
			userRepository.save(current);
			userRepository.save(toUnfriend);
			return true;
		}
		return false;
	}


	@Override
	public Collection<Employee> findEmployees(Restaurant restaurant) {
		// TODO Auto-generated method stub
		return employeeRepository.findByRestaurant(restaurant);
	}


	@Override
	public User findEmployee(String email) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(email);
	}


	@Override
	public User findByUsername(String username) {
		// TODO Auto-generated method stub
		return userRepository.findByEmail(username);
	}


	@Override
	public Collection<Guest> getGuests(UserType type, String name) {
		// TODO Auto-generated method stub
		
		return userRepository.findGuests(type, name);
	}

}
