package mrs.app.domain;
import javax.persistence.Entity;

@Entity
public class Chef extends Employee{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Chef() {
		// TODO Auto-generated constructor stub
		this.role = UserType.ROLE_CHEF;
	}
}
