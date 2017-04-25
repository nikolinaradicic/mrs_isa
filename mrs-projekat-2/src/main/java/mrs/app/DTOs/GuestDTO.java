package mrs.app.DTOs;

import java.util.ArrayList;

import mrs.app.domain.Guest;

public class GuestDTO {
	
	private String email;
	private String name;
	private String lastname;
	private ArrayList<String> friends;
	private ArrayList<String> requests;
	
	
	public GuestDTO(Guest guest){
		this.setEmail(guest.getEmail());
		this.setName(guest.getEmail());
		this.setLastname(guest.getLastname());
		friends = new ArrayList<String>();
		requests = new ArrayList<String>();
		for (Guest g : guest.getFriends()){
			this.friends.add(g.getEmail());
		}
		
		for (Guest g : guest.getRequests()){
			this.requests.add(g.getEmail());
		}
	}

	public GuestDTO(){}

	public String getLastname() {
		return lastname;
	}


	public void setLastname(String lastname) {
		this.lastname = lastname;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}
	
	public ArrayList<String> getFriends(){
		return friends;
	}
	
	public void setFriends(ArrayList<String> friends){
		this.friends = friends;
	}
	
	public ArrayList<String> getRequests(){
		return requests;
	}
	
	public void setRequests(ArrayList<String> requests){
		this.requests = requests;
	}
	
	
}
