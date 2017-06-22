package mrs.app.DTOs;

import java.util.ArrayList;
import java.util.List;

public class ChartDTO {
	
	private List<String> labels;
	private List<Double> data;
	
	public ChartDTO(){
		labels = new ArrayList<String>();
		data = new ArrayList<Double>();
	}

	public List<String> getLabels() {
		return labels;
	}

	public void setLabels(List<String> labels) {
		this.labels = labels;
	}

	public List<Double> getData() {
		return data;
	}

	public void setData(List<Double> data) {
		this.data = data;
	}
}
