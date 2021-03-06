package mrs.app.service;

import java.util.Collection;

import mrs.app.DTOs.ItemDrinkDTO;
import mrs.app.DTOs.ItemMealDTO;
import mrs.app.DTOs.MarkDTO;
import mrs.app.domain.Bartender;
import mrs.app.domain.Chef;
import mrs.app.domain.Guest;
import mrs.app.domain.Waiter;
import mrs.app.domain.restaurant.BartenderDrink;
import mrs.app.domain.restaurant.Bill;
import mrs.app.domain.restaurant.ChefMeal;
import mrs.app.domain.restaurant.ItemDrink;
import mrs.app.domain.restaurant.ItemMeal;
import mrs.app.domain.restaurant.Mark;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.Visit;
import mrs.app.domain.restaurant.WaiterOrd;

public interface OrderService {

	Collection<WaiterOrd> findAll();

	WaiterOrd setOrderMeal(WaiterOrd order,Restaurant restaurant,Waiter waiter);
	
	ChefMeal getOrderChef(ChefMeal order, Restaurant restaurant);
	
	Collection<ItemMeal> getAllMeals(Restaurant restaurant);

	Collection<WaiterOrd> getAllDrinks(Restaurant restaurant);

	BartenderDrink saveDrinks(BartenderDrink order, Restaurant restaurant);
	
	Collection<WaiterOrd> getMyOrder(Restaurant restaurant, Long id);

	ItemDrink updateItemDrink(ItemDrink itemDr);
	
	ItemMeal updateItemMeal(ItemMeal itemMe);
	
	ItemMeal updateItemMealStatus(ItemMeal itemMe, Chef chef);

	void deleteItemDrink(ItemDrinkDTO orderDTO);

	void deleteItemMeal(ItemMealDTO orderDTO);

	//Bill createCheck(Bill check);

	Visit createVisit(Bill bill);

	Collection<Visit> findMyVisits(Guest guest);

	Mark mark(MarkDTO markDTO);

	ItemDrink updateItemDrinkStatus(ItemDrink itemDr, Bartender b);

	Collection<WaiterOrd> findNotMine(Collection<WaiterOrd> defineOrder,
			Waiter waiter);
	
}
