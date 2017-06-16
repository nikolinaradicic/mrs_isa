package mrs.app.service.implementation;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
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
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

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
	public Offer findOne(Long id) {
		// TODO Auto-generated method stub
		return offerRepository.findOne(id);
	}

}
