package mrs.app.service.implementation;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.app.domain.restaurant.Segment;
import mrs.app.repository.SegmentRepository;
import mrs.app.service.SegmentService;

@Service
public class SegmentServiceImpl implements SegmentService {
	
	@Autowired
	private SegmentRepository segmentRepository;

	@Override
	public Segment create(Segment s) {
		// TODO Auto-generated method stub
		return segmentRepository.save(s);
	}

}
