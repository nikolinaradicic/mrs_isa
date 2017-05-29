package mrs.app.repository;

import mrs.app.domain.restaurant.Offer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OfferRepository extends JpaRepository<Offer, Long> {

}
