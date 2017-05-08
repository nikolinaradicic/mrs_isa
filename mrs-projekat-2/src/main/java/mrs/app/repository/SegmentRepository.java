package mrs.app.repository;

import mrs.app.domain.restaurant.Segment;

import org.springframework.data.jpa.repository.JpaRepository;

public interface SegmentRepository extends JpaRepository<Segment, Long> {

}
