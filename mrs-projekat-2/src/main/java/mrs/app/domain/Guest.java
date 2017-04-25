package mrs.app.domain;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;

import org.hibernate.annotations.Cascade;

@Entity
public class Guest extends User{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;	

	public Guest() {
		// TODO Auto-generated constructor stub
		this.role=UserType.GUEST;
	}
}
