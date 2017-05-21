package mrs.app.domain;
import javax.persistence.Entity;

@Entity
public class Waiter extends Employee{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Waiter() {
		// TODO Auto-generated constructor stub
		this.role = UserType.ROLE_WAITER;
		this.firstTime="notvisited";
	}
}
