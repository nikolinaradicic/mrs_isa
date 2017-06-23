package mrs.app.service;

import mrs.app.DTOs.ChartDTO;
import mrs.app.DTOs.QueryChartDTO;
import mrs.app.DTOs.RatingDTO;
import mrs.app.domain.restaurant.Restaurant;

public interface BillService {

	ChartDTO incomeChart(Restaurant restaurant, QueryChartDTO query);

	ChartDTO waitersChart(Restaurant restaurant, QueryChartDTO query);

	ChartDTO visitChart(Restaurant restaurant, QueryChartDTO query);

	RatingDTO getRatings(Restaurant restaurant);

}
