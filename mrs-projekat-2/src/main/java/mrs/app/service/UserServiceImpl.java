package mrs.app.service;

import java.util.Collection;
import mrs.app.domain.Guest;
import mrs.app.domain.User;
import mrs.app.repository.UserRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
    
    @Autowired
    private UserRepository userRepository;

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
        System.out.println(user.getEmail());
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
		System.out.println(user.getPassword());
		
		return userRepository.save(savedUser);
	}
	
	@Override
	public User changeData(User user) {
		User savedUser = userRepository.findOne(user.getId());
		System.out.println("*******************");
		System.out.println(user.toString());
		savedUser.setEmail(user.getEmail());
		savedUser.setName(user.getName());
		savedUser.setLastname(user.getLastname());
		
		return userRepository.save(savedUser);
	}
	
	@Override
	public boolean addFriend(Guest user, Guest friend) {
		// TODO Auto-generated method stub
		Guest currentUser = (Guest) userRepository.findOne(user.getId());
		
		Guest requestedFriend = (Guest) userRepository.findByEmail(friend.getEmail());
		
		if(requestedFriend != null){
			requestedFriend.getRequests().add(currentUser);
			
			userRepository.save(requestedFriend);
			return true;
		}
		return false;
	}


	@Override
	public boolean acceptFriend(Guest current, Guest friend) {
		// TODO Auto-generated method stub
		Guest currentUser = (Guest) userRepository.findOne(current.getId());
		
		Guest requestedFriend = (Guest) userRepository.findByEmail(friend.getEmail());
		if (requestedFriend != null)
		{
			requestedFriend.getFriends().add(currentUser);
			currentUser.getFriends().add(requestedFriend);
			currentUser.getRequests().remove(requestedFriend);
			userRepository.save(currentUser);
			userRepository.save(requestedFriend);
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

}
