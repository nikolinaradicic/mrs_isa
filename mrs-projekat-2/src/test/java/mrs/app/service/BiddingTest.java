package mrs.app.service;

import static org.junit.Assert.*;

import java.text.SimpleDateFormat;
import java.util.Date;

import mrs.app.DTOs.OfferDTO;
import mrs.app.domain.Bidder;
import mrs.app.domain.restaurant.GroceryList;
import mrs.app.domain.restaurant.Offer;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.repository.GroceryListRepository;
import mrs.app.repository.OfferRepository;
import mrs.app.repository.RestaurantRepository;
import mrs.app.repository.UserRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;


@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value = "test")
public class BiddingTest {
	
	@Autowired
	private GroceryListService glService;
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private UserRepository userRepository;
	
	@Autowired
	private OfferRepository offerRepository;
	
	@Autowired
	private GroceryListRepository glRepository;
	
	Restaurant rest;
	
	GroceryList gl;
	
	Offer offer;

	@Before
	public void setUp() throws Exception {
		rest = new Restaurant("neki", "opis");
		rest = restaurantRepository.save(rest);
		SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
		Date start = sdf.parse("16-06-2017");
		Date end = sdf.parse("20-06-2017");
		gl = glService.create(new GroceryList(rest, start, end, "daj svega"));
		Bidder b  = new Bidder("q", "q", "q","q");
		try{

			b = (Bidder) userRepository.save(b);
		}catch(Exception e){
			b = userRepository.findByEmail("q");
		}
		offer = new Offer(b, gl, 2000, "evo ti sve", false);
		glService.createOffer(offer, gl);
		offer = offerRepository.findByGroceryListAndBidder(gl, b);
	}
	
	
	@Test(expected = ObjectOptimisticLockingFailureException.class)
	public void testConcurrencyWriting() {

		GroceryList productForUserOne = glService.findOne(gl.getId());
		GroceryList productForUserTwo = glService.findOne(gl.getId());

		assertEquals(0, productForUserOne.getVersion().intValue());
		assertEquals(0, productForUserTwo.getVersion().intValue());
		Offer off = offerRepository.findOne(offer.getId());
		glService.acceptOffer(productForUserOne, off);
		assertEquals(1, glService.findOne(gl.getId()).getVersion().intValue());
		assertEquals(0, productForUserTwo.getVersion().intValue());
		
		OfferDTO offDto = new OfferDTO();
		offDto.setPrice(10);
		offDto.setMessage("nova");
		offDto.setId(offer.getId());
		glService.updateOffer(offDto, productForUserTwo);
		
	}


}
