package mrs.app.service;

import java.util.Collection;

import mrs.app.domain.restaurant.BartenderDrink;
import mrs.app.domain.restaurant.ChefMeal;
import mrs.app.domain.restaurant.Drink;
import mrs.app.domain.restaurant.Meal;
import mrs.app.domain.restaurant.WaiterOrd;
import mrs.app.domain.restaurant.Restaurant;

public interface OrderService {

	Collection<WaiterOrd> findAll();

	WaiterOrd setOrder(Collection<Meal> meals, Collection<Drink> drinks, Restaurant r);

	WaiterOrd setOrderMeal(WaiterOrd order,Restaurant restaurant);
	
	ChefMeal getOrderChef(ChefMeal order, Restaurant restaurant);
	
	Collection<Meal> getAllMeals(Restaurant restaurant);

	Collection<Drink> getAllDrinks(Restaurant restaurant);

	BartenderDrink saveDrinks(BartenderDrink order, Restaurant restaurant);
	
	
}
