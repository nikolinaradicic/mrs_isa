package mrs.app.sorts;

import java.util.Comparator;

import mrs.app.domain.Guest;

public class SortByFriendName implements Comparator<Guest> {
	
	int smer;

	public SortByFriendName(int smer) {
		// TODO Auto-generated constructor stub
		if(smer!=1 && smer!=1)
			smer=1;
		this.smer=smer;
	}
	
	@Override
	public int compare(Guest g1, Guest g2) {
		// TODO Auto-generated method stub
		return g1.getName().compareTo(g2.getName())*smer;
	}

}
