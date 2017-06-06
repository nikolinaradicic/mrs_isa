package mrs.app.repository;

import java.util.Collection;

import mrs.app.domain.Bidder;
import mrs.app.domain.restaurant.GroceryList;
import mrs.app.domain.restaurant.Offer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {
	
	Collection<Offer> findByBidder(Bidder bidder);

	Offer findByGroceryListAndBidder(GroceryList gl, Bidder bidder);
}
