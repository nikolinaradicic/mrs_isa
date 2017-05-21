package mrs.app.domain;

import javax.persistence.Entity;

@Entity
public class Bidder extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Bidder() {
		// TODO Auto-generated constructor stub
		this.role = UserType.ROLE_BIDDER;
		this.firstTime="notvisited";
	}
}
