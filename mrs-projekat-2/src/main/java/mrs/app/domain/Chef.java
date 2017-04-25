package mrs.app.domain;
import javax.persistence.Entity;

@Entity
public class Chef extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public Chef() {
		// TODO Auto-generated constructor stub
		this.role = UserType.CHEF;
	}
}
