package mrs.app.domain;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "waiter")
public class Waiter extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

}
