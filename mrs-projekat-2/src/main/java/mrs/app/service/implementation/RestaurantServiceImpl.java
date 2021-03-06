package mrs.app.service.implementation;

import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.app.domain.restaurant.Drink;
import mrs.app.domain.restaurant.Meal;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.repository.DrinkRepository;
import mrs.app.repository.MealRepository;
import mrs.app.repository.RestaurantRepository;
import mrs.app.service.RestaurantService;

@Service
public class RestaurantServiceImpl implements RestaurantService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private DrinkRepository drinkRepository;
	
	@Autowired
	private MealRepository mealRepository;
	
	@Override
	public Collection<Restaurant> findAll() {
		// TODO Auto-generated method stub
		logger.info("> findAll");
        Collection<Restaurant> restaurants = restaurantRepository.findAll();
        logger.info("< findAll");
        return restaurants;
	}

	@Override
	public Restaurant create(Restaurant restaurant) throws Exception {
		// TODO Auto-generated method stub
		logger.info("> create");
		
        if (restaurant.getId() != null) {
            logger.error("Pokusaj kreiranja novog entiteta, ali Id nije null.");
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        
        Restaurant savedRestaurant = restaurantRepository.save(restaurant);
        logger.info("< create");
        return savedRestaurant;
	}


	@Override
	public Drink addDrink(Drink drink) throws Exception {
		// TODO Auto-generated method stub
		logger.info("> create drink");
		
        if (drink.getId() != null) {
            logger.error("Pokusaj kreiranja novog entiteta, ali Id nije null.");
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        
        Drink savedDrink = drinkRepository.save(drink);
        
        logger.info("< create drink");
        return savedDrink;
		
	}

	@Override
	public Meal addMeal(Meal meal) throws Exception {
		// TODO Auto-generated method stub
		logger.info("> create meal");
		
        if (meal.getId() != null) {
            logger.error("Pokusaj kreiranja novog entiteta, ali Id nije null.");
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        
        Meal savedMeal = mealRepository.save(meal);
        
        logger.info("< create meal");
        return savedMeal;
	}

	@Override
	public int changeInformation(Restaurant restaurant) {
		// TODO Auto-generated method stub
		return restaurantRepository.updateRestaurant(restaurant.getName(), restaurant.getDescription(), restaurant.getId());
		
	}

	@Override
	public List<Restaurant> findAllSort() {
		// TODO Auto-generated method stub
		return restaurantRepository.findAllByOrderByNameAsc();
	}
	
	@Override
	 	public Restaurant findOne(Long id) {
	 		// TODO Auto-generated method stub
	 		
	 		return restaurantRepository.findOne(id);
	 	}

	@Override
	public Restaurant updateLocation(Restaurant restaurant) {
		// TODO Auto-generated method stub
		restaurantRepository.updateLocation(restaurant.getId(), restaurant.getLongitude(), restaurant.getLatitude());
		return restaurantRepository.findOne(restaurant.getId());
	}

	@Override
	public Meal updateMeal(Meal meal) {
		// TODO Auto-generated method stub
		int numUpdated = mealRepository.updateMeal(meal.getName(), meal.getDescription(), meal.getPrice(), meal.getId());
		if(numUpdated == 1){
			return meal;
		}
		return null;
	}

	@Override
	public Drink updateDrink(Drink drink) {
		// TODO Auto-generated method stub
		int numUpdated = drinkRepository.updateDrink(drink.getName(),drink.getDescription(),drink.getPrice(),drink.getId());
		if(numUpdated == 1){
			return drink;
		}
		return null;
	}

	@Override
	public boolean deleteMeal(Meal meal,Restaurant r) {
		// TODO Auto-generated method stub
		mealRepository.updateDeleteMeal(true, meal.getId(), r);
		return true;
	}

	@Override
	public boolean deleteDrink(Drink drink, Restaurant r) {
		// TODO Auto-generated method stub
		drinkRepository.updateDeleteDrink(true, drink.getId(), r);
		return true;
	}

}

