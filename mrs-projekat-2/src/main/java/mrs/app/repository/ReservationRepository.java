package mrs.app.repository;

import java.util.List;

import mrs.app.domain.User;
import mrs.app.domain.restaurant.Reservation;

import org.springframework.data.jpa.repository.JpaRepository;

public interface ReservationRepository extends  JpaRepository<Reservation, Long> {
	
	public List<Reservation> findAll();
	
	public <S extends Reservation> List<S> save(Iterable<S> arg0);
	

}
