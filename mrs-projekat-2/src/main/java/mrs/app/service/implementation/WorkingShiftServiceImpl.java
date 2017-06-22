package mrs.app.service.implementation;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.app.domain.Waiter;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.Shift;
import mrs.app.domain.restaurant.WorkingShift;
import mrs.app.repository.WorkingShiftRepository;
import mrs.app.service.WorkingShiftService;

@Service
public class WorkingShiftServiceImpl implements WorkingShiftService{
	
	@Autowired
	private WorkingShiftRepository wsRepository;

	@Override
	public WorkingShift create(WorkingShift ws) {
		// TODO Auto-generated method stub
		return wsRepository.save(ws);
	}

	@Override
	public Collection<WorkingShift> findForFilter(Restaurant restaurant,
			Date start, Date end) {
		// TODO Auto-generated method stub
		return wsRepository.findShifts(restaurant, start, end);
	}
	
	@Override
	public Collection<WorkingShift> findShiftsForEmployee(Restaurant restaurant) {
		Collection<WorkingShift> shifts=wsRepository.findShForRest(restaurant);
		return shifts;
	}

	@Override
	public Collection<WorkingShift> findByDateShiftRestaurant(Date date,
			Shift shift, Restaurant rest) {
		// TODO Auto-generated method stub
		return wsRepository.findByDateAndShiftAndRestaurant(date, shift, rest);
	}

	@Override
	public WorkingShift updateDate(WorkingShift workingShift) {
		// TODO Auto-generated method stub
		wsRepository.updateWorkingShift(workingShift.getDate(), workingShift.getId());
		return wsRepository.findOne(workingShift.getId());
	}

	@Override
	public boolean delete(WorkingShift workingShift) {
		// TODO Auto-generated method stub
		WorkingShift saved = wsRepository.findOne(workingShift.getId());
		if(saved != null){
			wsRepository.delete(saved);
			return true;
		}
		return false;
	}
	public WorkingShift findShiftForWaiter(Waiter current,String trenutnoVreme,
			Shift smena) {
		// TODO Auto-generated method stub
		WorkingShift shift=wsRepository.findShiftForWaiter(current, trenutnoVreme, smena);
		System.err.println(shift);
		if(shift==null){
			return null;
		}
		return shift;
	}

}
