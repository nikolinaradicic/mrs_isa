package mrs.app.repository;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import mrs.app.domain.RestaurantManager;
import mrs.app.domain.restaurant.BartenderDrink;
import mrs.app.domain.restaurant.Drink;
import mrs.app.domain.restaurant.ItemDrink;
import mrs.app.domain.restaurant.Meal;
import mrs.app.domain.restaurant.Restaurant;


import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BartenderDrinkRepositoryTest {

	@Autowired
	RestaurantRepository restaurantRepository;
	
	
	@Autowired
	DrinkRepository drinkRepository;
	
	@Autowired
	BartenderDrinkRepository bartenderDrinkRepository;
	
	@Test
	public void test() {
		Restaurant r=new Restaurant();
		r.setDescription("");
		r.setLatitude("");
		r.setManagers(null);
		r.setMenu(null);
		
		r.setDrinkList(null);
		r.setSegments(null);
		r.setLongitude("");
		r.setName("r");
		Restaurant rr=restaurantRepository.save(r);
		Drink drink=new Drink();
		drink.setDeleted(false);
		drink.setDescription("");
		drink.setName("pice");
		drink.setRestaurant(rr);
		drink.setPrice(1000);
		Drink d=drinkRepository.save(drink);
		BartenderDrink ord=new BartenderDrink();
		ord.setRestaurant(rr);
		ItemDrink id=new ItemDrink();
		id.setDrink(d);
		id.setQuantity(2);
		ord.getDrinks().add(id);
		BartenderDrink bd=bartenderDrinkRepository.save(ord);
		assertThat(bd).isNotNull();
		Collection<BartenderDrink> bdd=bartenderDrinkRepository.findByRest(rr);
		assertThat(bdd.size()).isGreaterThan(0);
	}

}
