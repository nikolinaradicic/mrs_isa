package mrs.app.service;

import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.Segment;

public interface SegmentService {
	public Segment create(Segment s);
	
	public Segment findSegment(String name, Restaurant restaurant);

	int updateSegment(String chart, Long id);
}
