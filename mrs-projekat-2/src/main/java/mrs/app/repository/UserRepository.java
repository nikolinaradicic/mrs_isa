package mrs.app.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import mrs.app.domain.User;

public interface UserRepository extends JpaRepository<User,Long>{
	
	public List<User> findAll();

	public <S extends User> List<S> save(Iterable<S> arg0);
	
	public <S extends User> S findByEmailAndPassword(String email, String password);
	
	public <S extends User> S findByEmail(String email);
}
