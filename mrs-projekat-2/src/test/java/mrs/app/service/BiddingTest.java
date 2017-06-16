package mrs.app.service;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import mrs.app.DTOs.OfferDTO;
import mrs.app.domain.Bidder;
import mrs.app.domain.restaurant.GroceryList;
import mrs.app.domain.restaurant.Offer;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.repository.OfferRepository;
import mrs.app.repository.RestaurantRepository;
import mrs.app.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
public class BiddingTest {
	
	@Autowired
	private GroceryListService glService;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OfferRepository offerRepository;

	@Before
	public void setUp() throws Exception {
		Restaurant rest = new Restaurant("neki", "opis");
		restaurantRepository.save(rest);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date start = sdf.parse("16-06-2017");
		Date end = sdf.parse("20-06-2017");
		GroceryList gl = new GroceryList(rest, start, end, "daj svega");
		Bidder b  = new Bidder("b", "b", "b","b");
		userRepository.save(b);
		glService.create(gl);
		Offer offer = new Offer(b, gl, 2000, "evo ti sve", false);
		glService.createOffer(offer, gl);
	}


	@Test(expected = ObjectOptimisticLockingFailureException.class)
	public void testConcurrencyWriting() {

		GroceryList productForUserOne = glService.findOne(1L);
		GroceryList productForUserTwo = glService.findOne(1L);

		assertEquals(0, productForUserOne.getVersion().intValue());
		assertEquals(0, productForUserTwo.getVersion().intValue());
		Offer offer = offerRepository.findOne(1L);
		glService.acceptOffer(productForUserOne, offer);
		assertEquals(1, glService.findOne(1L).getVersion().intValue());
		assertEquals(0, productForUserTwo.getVersion().intValue());
		
		OfferDTO off = new OfferDTO();
		off.setPrice(10);
		off.setMessage("nova");
		off.setId(offer.getId());
		glService.updateOffer(off, productForUserTwo);
		
	}


}
