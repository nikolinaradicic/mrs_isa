package mrs.app.service;

import java.util.Collection;

import mrs.app.domain.restaurant.Reservation;


public interface ReservationService  {
	
	Collection<Reservation> findAll();
	
	Reservation create(Reservation reservation) throws Exception;
	
}
