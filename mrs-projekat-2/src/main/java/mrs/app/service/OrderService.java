package mrs.app.service;

import java.util.Collection;

import mrs.app.domain.restaurant.Drink;
import mrs.app.domain.restaurant.Meal;
import mrs.app.domain.restaurant.Order;
import mrs.app.domain.restaurant.Restaurant;

public interface OrderService {

	Collection<Order> findAll();

	Order setOrder(Collection<Meal> meals, Collection<Drink> drinks, Restaurant r);
	
}
