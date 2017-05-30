package mrs.app.domain.restaurant;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

@Entity
public class BartenderDrink implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;	
	
	@ManyToMany(cascade={CascadeType.ALL},fetch = FetchType.LAZY)
	public Collection<Drink> drinks;
	
	public BartenderDrink() {
		// TODO Auto-generated constructor stub
		this.drinks=new ArrayList<Drink>();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Collection<Drink> getDrinks() {
		return drinks;
	}

	public void setDrinks(Collection<Drink> drinks) {
		this.drinks = drinks;
	}

}
