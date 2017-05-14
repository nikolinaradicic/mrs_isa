package mrs.app.DTOs;

import java.util.Date;

public class WorkingShiftDTO {
	private Date start;
	private Date end;
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
}
