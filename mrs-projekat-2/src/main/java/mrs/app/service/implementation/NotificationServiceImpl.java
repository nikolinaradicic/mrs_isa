package mrs.app.service.implementation;

import java.util.Collection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import mrs.app.domain.Notification;
import mrs.app.domain.User;
import mrs.app.repository.NotificationRepository;
import mrs.app.service.NotificationService;

@Service
public class NotificationServiceImpl implements NotificationService{
	
	@Autowired
	private NotificationRepository notificationRepository;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public Notification create(Notification notif) throws Exception {
		// TODO Auto-generated method stub
		logger.info("> create");
		
        if (notif.getId() != null) {
            logger.error("Pokusaj kreiranja novog entiteta, ali Id nije null.");
            throw new Exception("Id mora biti null prilikom perzistencije novog entiteta.");
        }
        
        Notification saved = notificationRepository.save(notif);
        
        logger.info("< create");
        return saved;

	}

	@Override
	public Collection<Notification> findByUser(User user) {
		// TODO Auto-generated method stub
		return notificationRepository.findByUserAndSeen(user, false);
	}

	@Override
	public Notification updateNotification(Long id, User saved) {
		// TODO Auto-generated method stub
		Notification notif = notificationRepository.findOne(id);
		if (notif.getUser().equals(saved)){
			notif.setSeen(true);
			return notificationRepository.save(notif);
		}
		return null;
	}

}
