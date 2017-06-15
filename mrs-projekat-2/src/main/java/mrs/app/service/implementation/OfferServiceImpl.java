package mrs.app.service.implementation;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import mrs.app.domain.Bidder;
import mrs.app.domain.restaurant.GroceryList;
import mrs.app.domain.restaurant.Offer;
import mrs.app.repository.OfferRepository;
import mrs.app.service.OfferService;

@Service
public class OfferServiceImpl implements OfferService{
	
	@Autowired
	private OfferRepository offerRepository;
	

	@Autowired
	SimpMessagingTemplate simp;
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Offer create(Offer o) throws Exception {
		// TODO Auto-generated method stub
		logger.info("> create");
        if (o.getId() != null) {
            logger.error("Pokusaj kreiranja novog entiteta, ali Id nije null.");
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        Offer saved = offerRepository.findByGroceryListAndBidder(o.getGroceryList(), o.getBidder());
        if (saved != null){
        	saved.setMessage(o.getMessage());
        	saved.setPrice(o.getPrice());
        	logger.info("< create");
    		return offerRepository.save(saved);
        }
        logger.info("< create");
		return offerRepository.save(o);

	}

	@Override
	public Collection<Offer> findForBidder(Bidder user) {
		// TODO Auto-generated method stub
		return offerRepository.findByBidder(user);
	}

	@Override
	public Offer findByListAndBidder(GroceryList gl, Bidder user) {
		// TODO Auto-generated method stub
		return offerRepository.findByGroceryListAndBidder(gl, user);
	}

	@Override
	public Offer acceptOffer(Offer offer) {
		// TODO Auto-generated method stub
		Offer saved = offerRepository.findOne(offer.getId());
		saved.setAccepted(true);
		
		offerRepository.save(saved);
		
		String text = "vasa ponuda je prihvacena.";		
				
		simp.convertAndSend("/notify/" + saved.getBidder().getEmail() + "/receive", text);
						
		return saved;
	}

}
