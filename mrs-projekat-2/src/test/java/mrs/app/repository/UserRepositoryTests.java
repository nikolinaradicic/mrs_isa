package mrs.app.repository;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import mrs.app.domain.Guest;
import mrs.app.domain.SystemManager;
import mrs.app.domain.User;
import mrs.app.domain.UserType;
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
		SystemManager user = new SystemManager();
		user.setEmail("abc");
		user.setPassword("abc");
		user.setLastname("ja");
		user.setName("ja");
		userRepository.save(user);
		
		List<User> users = userRepository.findAll();
		assertThat(users.size()).isGreaterThan(1);
		
		User saved = userRepository.findByEmailAndPassword(user.getEmail(), user.getPassword());
		assertThat(saved).isNotNull();
		
		User saved1=userRepository.findByEmail(user.getEmail());
		assertThat(saved1).isNotNull();
		
		Guest guest1=new Guest();
		guest1.setEmail("gostt1");
		guest1.setLastname("");
		guest1.setName("goran");
		guest1.setPassword("g");
		guest1.setRole(UserType.ROLE_GUEST);
		
		Guest guest2=new Guest();
		guest2.setEmail("go");
		guest2.setLastname("");
		guest2.setName("ggg");
		guest2.setPassword("g");
		guest2.setRole(UserType.ROLE_GUEST);
		
		Guest gg= userRepository.save(guest1);
		assertThat(gg).isNotNull();
		Guest gg1=userRepository.save(guest2);
		assertThat(gg).isNotNull();
		//List<Guest> guests=userRepository.findGuests(UserType.ROLE_GUEST, "g");
		//assertThat(guests.size()).isEqualTo(2);
	}

}
