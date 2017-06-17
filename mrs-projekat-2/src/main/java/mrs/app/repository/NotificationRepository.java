package mrs.app.repository;

import java.util.Collection;

import mrs.app.domain.Notification;
import mrs.app.domain.User;

import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

	Collection<Notification> findByUserAndSeen(User user, boolean seen);
}
