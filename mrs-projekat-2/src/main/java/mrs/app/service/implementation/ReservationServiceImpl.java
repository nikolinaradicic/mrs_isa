package mrs.app.service.implementation;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mrs.app.domain.User;
import mrs.app.domain.restaurant.Reservation;
import mrs.app.repository.ReservationRepository;
import mrs.app.service.ReservationService;

public class ReservationServiceImpl implements ReservationService{
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Collection<Reservation> findAll() {
		// TODO Auto-generated method stub
		logger.info("> findAll reservation");
        Collection<Reservation> reservations = reservationRepository.findAll();
        logger.info("< findAll reservation");
        return reservations;
	}

	@Override
	public Reservation create(Reservation r) throws Exception {
		// TODO Auto-generated method stub
		logger.info("> create");
        if (r.getId() != null) {
            logger.error(
                    "Pokusaj kreiranja novog entiteta, ali Id nije null.");
            throw new Exception(
                    "Id mora biti null prilikom perzistencije novog entiteta.");
        }
        
        Reservation savedReservation = reservationRepository.save(r);
        logger.info("< create");
        return savedReservation;
	}

}
