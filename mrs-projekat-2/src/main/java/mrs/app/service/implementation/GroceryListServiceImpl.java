package mrs.app.service.implementation;

import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import mrs.app.DTOs.OfferDTO;
import mrs.app.domain.restaurant.GroceryList;
import mrs.app.domain.restaurant.Offer;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.repository.GroceryListRepository;
import mrs.app.repository.OfferRepository;
import mrs.app.service.GroceryListService;

@Service
@Transactional(readOnly = true)
public class GroceryListServiceImpl implements GroceryListService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GroceryListRepository groceryListRepository;

	@Autowired
	private OfferRepository offerRepository;
	
	@Override
	@Transactional(readOnly = false)
	public GroceryList create(GroceryList gl) throws Exception {
		// TODO Auto-generated method stub
		logger.info("> create");
        if (gl.getId() != null) {
            logger.error("Pokusaj kreiranja novog entiteta, ali Id nije null.");
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        logger.info("< create");
		return groceryListRepository.save(gl);

	}

	@Override
	public Collection<GroceryList> findByRestaurant(Restaurant r, Date date) {
		// TODO Auto-generated method stub
		return groceryListRepository.findActive(r, date);
	}

	@Override
	public GroceryList findOne(Long id) {
		// TODO Auto-generated method stub
		return groceryListRepository.findOne(id);
	}

	@Override
	public Collection<GroceryList> findAllActive(Date start) {
		// TODO Auto-generated method stub
		return groceryListRepository.findAllActive(start);
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Offer createOffer(Offer o, GroceryList gl) {
		// TODO Auto-generated method stub
		logger.info("> create");
        gl.getOffers().add(o);
        groceryListRepository.save(gl);
        logger.info("< create");
		return o;

	}
	
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public Offer updateOffer(OfferDTO o, GroceryList gl) {
		// TODO Auto-generated method stub
		logger.info("> update");
        for(Offer off : gl.getOffers()){
        	if(off.getId().equals(o.getId())){
        		off.setMessage(o.getMessage());
        		off.setPrice(o.getPrice());
                groceryListRepository.save(gl);
                logger.info("< update");
        		return off;
        	}
        }
        return null;
	}
	
	@Override
	@Transactional(readOnly = false, propagation = Propagation.REQUIRES_NEW)
	public GroceryList acceptOffer(GroceryList gl, Offer offer) {
		// TODO Auto-generated method stub
		offer.setAccepted(true);
		gl.setAcceptedOffer(offer);
		groceryListRepository.save(gl);
						
		return gl;
	}

}
