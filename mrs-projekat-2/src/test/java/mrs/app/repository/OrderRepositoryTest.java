package mrs.app.repository;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Date;

import mrs.app.DTOs.TableDTO;
import mrs.app.domain.Employee;
import mrs.app.domain.User;
import mrs.app.domain.Waiter;
import mrs.app.domain.restaurant.Drink;
import mrs.app.domain.restaurant.ItemDrink;
import mrs.app.domain.restaurant.ItemMeal;
import mrs.app.domain.restaurant.Meal;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.RestaurantTable;
import mrs.app.domain.restaurant.Segment;
import mrs.app.domain.restaurant.WaiterOrd;

import org.hibernate.mapping.Collection;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
 
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderRepositoryTest {
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Autowired
	MealRepository mealRepository;
	
	@Autowired
	DrinkRepository drinkRepository;
	
	@Autowired
	ItemDrinkRepository itemDrinkRepository;
	
	@Autowired
	ItemMealRepository itemMealRepository;
	
	@Autowired
	SegmentRepository segmentRepository;
	
	@Autowired
	RestaurantTableRepository restaurantTableRepository;
	
	@Autowired
	EmployeeRepository employeeRepository;
	
	@Autowired
	UserRepository userRepository;
	
	Restaurant restaurant;
	Meal meal;
	Drink drink;
	ItemMeal itemMeal;
	ItemDrink itemDrink;
	ArrayList<ItemMeal> itemMeals=new ArrayList<>();
	ArrayList<ItemDrink> itemDrinks=new ArrayList<>();
	ArrayList<RestaurantTable> tables=new ArrayList<>();
	Segment segment;
	RestaurantTable table;
	TableDTO tableDTO;
	Waiter waiter;
	User user;
	Employee employee;
	WaiterOrd order;
	
	@Before
	public void SetUp(){
		restaurant = restaurantRepository.save(new Restaurant("restoran", "opis"));	
		meal=mealRepository.save(new Meal("jelo","opis",200,restaurant));
		drink=drinkRepository.save(new Drink("pice","pice",50,restaurant));
		itemMeal=new ItemMeal(meal,2);
		itemDrink=new ItemDrink(drink,2);
		itemMeals.add(itemMeal);
		itemDrinks.add(itemDrink);
		segment=segmentRepository.save(new Segment("Basta", restaurant));
		tableDTO=new TableDTO(3, "sto1");
		table=restaurantTableRepository.save(new RestaurantTable(tableDTO, segment));
		waiter=(Waiter)userRepository.save(new Waiter(new Date(), 22, 22, restaurant, "b", "b", "1", "b"));
	}
	
	@Test
	public void executesQueryMethodsCorrectly(){
		order=new WaiterOrd();
		order.setWaiter(waiter);
		order.setRestaurant(restaurant);
		order.setTable(table);
		order.setDrinks(itemDrinks);
		order.setMeals(itemMeals);
		orderRepository.save(order);
		itemMealRepository.updateOrder(order, itemMeal.getId());
		itemDrinkRepository.updateOrder(order, itemDrink.getId());
		//treba da mi vrati 1 po restoranu
		ArrayList<WaiterOrd> kolekcija=(ArrayList<WaiterOrd>) orderRepository.findByRest(restaurant);
		assertThat(kolekcija.size()).isEqualTo(1);
		ArrayList<WaiterOrd> kolekcija1=(ArrayList<WaiterOrd>) orderRepository.findByWaiter(waiter);
		assertThat(kolekcija1.size()).isEqualTo(1);
		WaiterOrd all= orderRepository.findOne(order.getId());
		assertThat(all).isNotNull();
	}	

 }