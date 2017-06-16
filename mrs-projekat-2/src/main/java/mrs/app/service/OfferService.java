package mrs.app.service;

import java.util.Collection;

import mrs.app.domain.Bidder;
import mrs.app.domain.restaurant.GroceryList;
import mrs.app.domain.restaurant.Offer;

public interface OfferService {

	public Offer create(Offer o) throws Exception;

	public Collection<Offer> findForBidder(Bidder user);

	public Offer findByListAndBidder(GroceryList gl, Bidder user);
	
	public Offer acceptOffer(Offer offer);
}
