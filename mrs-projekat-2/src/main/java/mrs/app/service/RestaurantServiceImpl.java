package mrs.app.service;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.app.domain.Restaurant;
import mrs.app.domain.RestaurantManager;
import mrs.app.repository.RestaurantRepository;

@Service
public class RestaurantServiceImpl implements RestaurantService{
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RestaurantRepository restaurantRepository;
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
	public Restaurant addManager(RestaurantManager manager, Long id) throws Exception {
		// TODO Auto-generated method stub
		logger.info("> add Manager");
        Restaurant savedRestaurant = restaurantRepository.findOne(id);
        savedRestaurant.getManagers().add(manager);
        restaurantRepository.save(savedRestaurant);
        logger.info("< add Manager");
        return savedRestaurant;
	}

}
