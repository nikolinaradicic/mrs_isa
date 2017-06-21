package mrs.app.repository;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;

import mrs.app.DTOs.TableDTO;
import mrs.app.domain.Employee;
import mrs.app.domain.User;
import mrs.app.domain.UserType;
import mrs.app.domain.Waiter;
import mrs.app.domain.restaurant.Drink;
import mrs.app.domain.restaurant.ItemDrink;
import mrs.app.domain.restaurant.ItemMeal;
import mrs.app.domain.restaurant.Meal;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.RestaurantTable;
import mrs.app.domain.restaurant.Segment;
import mrs.app.domain.restaurant.WaiterOrd;

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
	
	@Before
	public void SetUp(){
		restaurant = restaurantRepository.save(new Restaurant("restoran", "opis"));	
		meal=mealRepository.save(new Meal("jelo","opis",200,restaurant));
		drink=drinkRepository.save(new Drink("pice","pice",50,restaurant));
		itemMeal=itemMealRepository.save(new ItemMeal(meal,2));
		itemDrink=itemDrinkRepository.save(new ItemDrink(drink, 2));
		itemMeals=(ArrayList<ItemMeal>) itemMealRepository.findAll();
		itemDrinks=(ArrayList<ItemDrink>) itemDrinkRepository.findAll();
		segment=segmentRepository.save(new Segment("Basta", restaurant));
		tableDTO=new TableDTO(3, "sto1");
		table=restaurantTableRepository.save(new RestaurantTable(tableDTO, segment));
		tables.add(table);
		user=userRepository.save(new User("sifra", "name", "lastname", "email", UserType.ROLE_WAITER));
		
	}
	
	@Test
	public void executesQueryMethodsCorrectly(){

		orderRepository.save(new WaiterOrd(null, null, restaurant, null, table));
		List<WaiterOrd> all= orderRepository.findAll();
		assertThat(all.size()).isGreaterThan(0);
	}	
 }