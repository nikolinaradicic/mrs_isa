package mrs.app.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "systemmanager")
public class SystemManager extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	

}
