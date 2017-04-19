package mrs.app.service;

import java.util.Collection;

import mrs.app.domain.User;

public interface UserService {
	
	User login(String username, String password);
	
	Collection<User> findAll();

	User create(User user) throws Exception;
}
