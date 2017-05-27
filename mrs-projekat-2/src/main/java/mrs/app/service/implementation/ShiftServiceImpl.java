package mrs.app.service.implementation;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.Shift;
import mrs.app.repository.ShiftRepository;
import mrs.app.service.ShiftService;

@Service
public class ShiftServiceImpl implements ShiftService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ShiftRepository shiftRepository;

	@Override
	public Shift create(Shift s) throws Exception {
		// TODO Auto-generated method stub
		logger.info("> create");
		
        if (s.getId() != null) {
            logger.error("Pokusaj kreiranja novog entiteta, ali Id nije null.");
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        logger.info("< create");
		return shiftRepository.save(s);
	}

	@Override
	public Collection<Shift> findShifts(Restaurant restaurant) {
		// TODO Auto-generated method stub
		return shiftRepository.findByRestaurant(restaurant);
	}

}
