package mrs.app.service.implementation;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.Segment;
import mrs.app.repository.SegmentRepository;
import mrs.app.service.SegmentService;

@Service
public class SegmentServiceImpl implements SegmentService {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private SegmentRepository segmentRepository;

	@Override
	public Segment create(Segment s) throws Exception{
		// TODO Auto-generated method stub
		logger.info("> create");
		
        if (s.getId() != null) {
            logger.error("Pokusaj kreiranja novog entiteta, ali Id nije null.");
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        logger.info("< create");
		return segmentRepository.save(s);
	}

	@Override
	public Segment findSegment(String name, Restaurant restaurant) {
		// TODO Auto-generated method stub
		return segmentRepository.findByNameAndRestaurant(name, restaurant);
	}

	@Override
	public int updateSegment(String chart, Long id){
		return segmentRepository.updateSegment(chart, id);
	}

	@Override
	public Collection<Segment> findForRestaurant(Restaurant restaurant) {
		// TODO Auto-generated method stub
		return segmentRepository.findByRestaurant(restaurant);
	}


}
