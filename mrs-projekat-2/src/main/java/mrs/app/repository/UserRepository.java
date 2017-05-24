package mrs.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import mrs.app.domain.User;
import mrs.app.domain.UserType;

public interface UserRepository extends JpaRepository<User,Long>{
	
	public List<User> findAll();

	public <S extends User> List<S> save(Iterable<S> arg0);
	
	public <S extends User> S findByEmailAndPassword(String email, String password);
	
	public <S extends User> S findByEmail(String email);
	
	@Query("select u from User u where u.role=?1 and u.name like ?2")
	public <S extends User> List<S> findGuests(UserType type, String name);
}
