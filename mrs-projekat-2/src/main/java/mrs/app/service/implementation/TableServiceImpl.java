package mrs.app.service.implementation;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.app.DTOs.TableDTO;
import mrs.app.domain.restaurant.RestaurantTable;
import mrs.app.domain.restaurant.Segment;
import mrs.app.repository.RestaurantTableRepository;
import mrs.app.service.TableService;

@Service
public class TableServiceImpl implements TableService {
	
	@Autowired
	private RestaurantTableRepository tableRepository;
	
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

	@Override
	public boolean saveTables(List<TableDTO> tables, Segment segment) {
		// TODO Auto-generated method stub
		for (TableDTO table : tables){
			if (table.getOperation().equals("new")){
				tableRepository.save(new RestaurantTable(table, segment));
			}
			else if(table.getOperation().equals("delete")){
				RestaurantTable saved = tableRepository.findByNameAndSegment(table.getName(), segment);
				tableRepository.delete(saved.getId());
			}
			else if(table.getOperation().equals("update")){
				/*RestaurantTable saved = tableRepository.findByNameAndSegment(table.getName(), segment);
				saved.setChairNumber(table.getChairNum());
				tableRepository.save(saved);*/
				tableRepository.updateRestaurantTable(table.getChairNum(), table.getName(), segment);
			}
			else{
				return false;
			}
		}
		return true;
	}

	@Override
	public RestaurantTable findByNameAndSegment(String name, Segment segment) {
		// TODO Auto-generated method stub
		return tableRepository.findByNameAndSegment(name, segment);
	}



}
