package mrs.app.repository;

import java.util.Collection;
import java.util.List;

import mrs.app.domain.User;
import mrs.app.domain.restaurant.Reservation;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReservationRepository extends  JpaRepository<Reservation, Long> {
	
	public List<Reservation> findAll();
	
	public <S extends Reservation> List<S> save(Iterable<S> arg0);
	
	@Query("select r from Reservation r where DATE_FORMAT(r.date,'%d.%m.%Y')=?1")
	public Collection<Reservation> getReservByDate(String date);
	

}
