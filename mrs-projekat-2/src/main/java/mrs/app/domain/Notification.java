package mrs.app.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;


@Entity
public class Notification {
	@Id
	@GeneratedValue
	private Long id;

	@ManyToOne
	@JoinColumn(nullable=false)
	private User user;
	
	@Column(nullable = false)
	private String text;
	
	@Column(nullable = false)
	private boolean seen;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Notification(User user, boolean seen) {
		super();
		this.user = user;
		this.seen = seen;
	}

	public Notification() {
		super();
		// TODO Auto-generated constructor stub
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public boolean isSeen() {
		return seen;
	}

	public void setSeen(boolean seen) {
		this.seen = seen;
	}

}
