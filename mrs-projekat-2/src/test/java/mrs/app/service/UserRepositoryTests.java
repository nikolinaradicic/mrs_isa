package mrs.app.service;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import mrs.app.domain.User;
import mrs.app.repository.UserRepository;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;



@RunWith(SpringRunner.class)
@SpringBootTest
public class UserRepositoryTests {
	
	@Autowired
	UserRepository userRepository;
	
	@Test
	public void executesQueryMethodsCorrectly(){
		User user = new User("bla", "ja","ja","ja");
		userRepository.save(user);
		
		List<User> users = userRepository.findAll();
		assertThat(users.size()).isGreaterThan(1);
		
		User saved = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
		assertThat(saved).isNotNull();
	}

}
