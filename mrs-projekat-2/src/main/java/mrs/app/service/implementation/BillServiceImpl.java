package mrs.app.service.implementation;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.app.DTOs.ChartDTO;
import mrs.app.DTOs.QueryChartDTO;
import mrs.app.domain.Waiter;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.Visit;
import mrs.app.repository.EmployeeRepository;
import mrs.app.repository.VisitRepository;
import mrs.app.service.BillService;

@Service
public class BillServiceImpl implements BillService{
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	VisitRepository visitRepository;

	@Override
	public ChartDTO incomeChart(Restaurant restaurant, QueryChartDTO query) {
		// TODO Auto-generated method stub
		Collection<Visit> found = visitRepository.findForDate(restaurant, query.getStart(), query.getEnd());
		Calendar cal = Calendar.getInstance();
		cal.setTime(query.getEnd());
		  cal.set(Calendar.HOUR, 23);
		  cal.set(Calendar.MINUTE, 59);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

		StringBuilder str = new StringBuilder();
		ChartDTO result = new ChartDTO();
		Date start = query.getStart();
		while(true){
			if (start.compareTo(query.getEnd()) > 0)
				break;
			str.append(dateFormat.format(start));
			result.getLabels().add(str.toString());
			result.getData().add(0.0);
			
			cal.setTime(start);
			cal.add(Calendar.DAY_OF_YEAR, 1);
			start  = cal.getTime();
			str.delete(0, str.length());
		}
		
		for (Visit p: found){
			str.append(dateFormat.format(p.getDate()));
			int index = result.getLabels().indexOf(str.toString()); 
			if (index == -1){
				result.getLabels().add(str.toString());
				result.getData().add(p.getBill().getFinal_price());
			} else {
				result.getData().set(index, result.getData().get(index) + p.getBill().getFinal_price());
			}
			str.delete(0, str.length());
		}
		

		return result;
	}


	@Override
	public ChartDTO waitersChart(Restaurant restaurant, QueryChartDTO query) {
		// TODO Auto-generated method stub
		
		ChartDTO result = new ChartDTO();
		Collection<Waiter> waiters = employeeRepository.findWaiters(restaurant);
		for (Waiter w : waiters){
			result.getLabels().add(w.getName() + " " + w.getLastname());
			System.out.println(w.getEmail());
			try{

				double earned = visitRepository.sumForWaiters(restaurant, w, query.getStart(),query.getEnd());
				result.getData().add(earned);
			}catch(Exception e){
				result.getData().add(0.0);
			}
		}
		return result;
	}


	@Override
	public ChartDTO visitChart(Restaurant restaurant, QueryChartDTO query) {
		// TODO Auto-generated method stub
		ChartDTO result = new ChartDTO();
		Collection<Visit> found = visitRepository.findForDate(restaurant, query.getStart(), query.getEnd());
		Calendar cal = Calendar.getInstance();
		cal.setTime(query.getEnd());
		cal.set(Calendar.HOUR, 23);
		cal.set(Calendar.MINUTE, 59);
		query.setEnd(cal.getTime());
		
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");

		StringBuilder str = new StringBuilder();
		
 
		  
		Date pocetak = query.getStart();
		while(true){
			if (pocetak.compareTo(query.getEnd()) > 0)
				break;
			str.append(dateFormat.format(pocetak));
			result.getLabels().add(str.toString());
			result.getData().add(0.0);
			
			cal.setTime(pocetak);
			cal.add(Calendar.DAY_OF_YEAR, 1);
			pocetak  = cal.getTime();
			str.delete(0, str.length());
		}
		
		for (Visit v: found){
			if (v.getBill() == null)
				continue;
			str.append(dateFormat.format(v.getDate()));

			int index = result.getLabels().indexOf(str.toString()); 
			if (index == -1){
				result.getLabels().add(str.toString());
				result.getData().add(1.0);
			} else {
				result.getData().set(index, result.getData().get(index) + 1);
			}
			str.delete(0, str.length());
		}
		
		return result;
	}

}
