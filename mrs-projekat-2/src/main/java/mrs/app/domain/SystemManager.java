package mrs.app.domain;
import javax.persistence.Entity;

@Entity
public class SystemManager extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	public SystemManager() {
		// TODO Auto-generated constructor stub
		this.role = UserType.SYSTEM_MANAGER;
		this.firstTime="notvisited";
	}
	

}
