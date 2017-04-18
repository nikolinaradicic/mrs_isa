package mrs.app.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "guest")
public class Guest extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
