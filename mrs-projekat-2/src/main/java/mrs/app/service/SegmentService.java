package mrs.app.service;

import java.util.Collection;

import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.Segment;

public interface SegmentService {
	
	public Segment create(Segment s) throws Exception;
	
	
	public Segment findSegment(String name, Restaurant restaurant);

	int updateSegment(String chart, Long id);
	
	public Collection<Segment> findForRestaurant(Restaurant restaurant);
}
