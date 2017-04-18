package mrs.app.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "bartender")
public class Bartender extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
