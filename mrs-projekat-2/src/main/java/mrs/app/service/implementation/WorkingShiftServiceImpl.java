package mrs.app.service.implementation;

import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.app.domain.restaurant.Restaurant;
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

}
