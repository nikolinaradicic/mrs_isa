package mrs.app.service.implementation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import mrs.app.domain.restaurant.RestaurantTable;
import mrs.app.repository.TableRepository;
import mrs.app.service.TableService;

@Service
public class TableServiceImpl implements TableService {
	
	@Autowired
	TableRepository tableRepository;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public RestaurantTable create(RestaurantTable table) throws Exception {
		// TODO Auto-generated method stub
		logger.info("> create");
		
        if (table.getId() != null) {
            logger.error("Pokusaj kreiranja novog entiteta, ali Id nije null.");
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        
        RestaurantTable savedTable = tableRepository.save(table);
        
        logger.info("< create");
        return savedTable;
	}



}
