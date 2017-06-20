package mrs.app.service;

import java.util.Collection;

import mrs.app.domain.Notification;
import mrs.app.domain.User;

public interface NotificationService {
	Notification create(Notification notif) throws Exception;
	Collection<Notification> findByUser(User user);
	Notification updateNotification(Long id, User saved);
}
