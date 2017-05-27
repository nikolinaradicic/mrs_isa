package mrs.app.service;

import java.util.Collection;

import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.Shift;

public interface ShiftService {
	public Shift create(Shift s) throws Exception;
	
	public Collection<Shift> findShifts(Restaurant restaurant);

}
