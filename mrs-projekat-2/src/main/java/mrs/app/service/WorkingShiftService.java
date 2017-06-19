package mrs.app.service;

import java.util.Collection;
import java.util.Date;

import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.Shift;
import mrs.app.domain.restaurant.WorkingShift;


public interface WorkingShiftService {
	public WorkingShift create(WorkingShift ws);
	public Collection<WorkingShift> findForFilter(Restaurant restaurant, Date start, Date end);
	public Collection<WorkingShift> findShiftsForEmployee(Restaurant restaurant);
	
	public Collection<WorkingShift> findByDateShiftRestaurant(Date date, Shift shift, Restaurant rest);
	public WorkingShift updateDate(WorkingShift workingShift);
	public boolean delete(WorkingShift workingShift);
}
