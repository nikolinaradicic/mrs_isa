package mrs.app.service;

import java.util.Collection;

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
		User savedUser=userRepository.save(user);
		System.out.println(user.getId());
		return savedUser;
	}

}
