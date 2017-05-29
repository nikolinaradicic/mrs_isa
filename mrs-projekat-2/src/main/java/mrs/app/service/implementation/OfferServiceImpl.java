package mrs.app.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import mrs.app.domain.restaurant.Offer;
import mrs.app.repository.OfferRepository;
import mrs.app.service.OfferService;

public class OfferServiceImpl implements OfferService{
	
	@Autowired
	private OfferRepository offerRepository;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Offer create(Offer o) throws Exception {
		// TODO Auto-generated method stub
		logger.info("> create");
        if (o.getId() != null) {
            logger.error("Pokusaj kreiranja novog entiteta, ali Id nije null.");
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        logger.info("< create");
		return offerRepository.save(o);

	}

}
