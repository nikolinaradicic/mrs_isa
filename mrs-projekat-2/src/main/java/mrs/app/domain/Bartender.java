package mrs.app.domain;

import javax.persistence.Entity;

@Entity
public class Bartender extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public Bartender() {
		// TODO Auto-generated constructor stub
		this.role=UserType.BARTENDER;
	}
	


}
