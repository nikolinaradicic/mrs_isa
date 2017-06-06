package mrs.app.service.implementation;

import java.util.Collection;
import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.app.domain.RestaurantManager;
import mrs.app.domain.restaurant.GroceryList;
import mrs.app.repository.GroceryListRepository;
import mrs.app.service.GroceryListService;

@Service
public class GroceryListServiceImpl implements GroceryListService {

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private GroceryListRepository groceryListRepository;

	@Override
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
	public Collection<GroceryList> findByManager(RestaurantManager user, Date date) {
		// TODO Auto-generated method stub
		return groceryListRepository.findActive(user, date);
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

}
