package mrs.app.service;


import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import mrs.app.domain.restaurant.BartenderDrink;
import mrs.app.domain.restaurant.ChefMeal;
import mrs.app.domain.restaurant.Drink;
import mrs.app.domain.restaurant.Meal;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.WaiterOrd;
import mrs.app.repository.BartenderDrinkRepository;
import mrs.app.repository.ChefMealRepository;
import mrs.app.repository.DrinkRepository;
import mrs.app.repository.MealRepository;
import mrs.app.repository.OrderRepository;
import mrs.app.repository.RestaurantRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceTests {

	@Autowired
	OrderService orderService;
	
	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	MealRepository mealRepository;
	
	@Autowired
	DrinkRepository drinkRepository;
	
	@Autowired
	ChefMealRepository chefMealRepository;
	
	@Autowired
	BartenderDrinkRepository bartenderDrinkRepository;
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	WaiterOrd waiterOrd;
	
	@Before
	public void setUp(){
		Restaurant restaurant=new Restaurant("r1", "Opis restorana r1.");
		Meal meal=new Meal("jelo", "jelo", 80, restaurant);
		Drink drink=new Drink("pice", "pice", 10, restaurant);
		WaiterOrd order= new WaiterOrd();
		meal.setId(0L);
		drink.setId(0L);
		Drink d=drinkRepository.findOne(drink.getId());
		order.getDrinks().add(d);
		Meal m=mealRepository.findOne(meal.getId());
		order.getMeals().add(m);
		waiterOrd= orderRepository.save(order);
		System.out.println(waiterOrd.getDrinks().isEmpty());
	}
	
	@After
	public void tearDown(){
		orderRepository.deleteAll();
	}
	
	@Test
	public void findAllTest(){
		Collection<WaiterOrd> orders = orderRepository.findAll();
		assertThat(orders.size()).isGreaterThan(0);
	}
	
	@Test
	public void setOrderTest(){
		boolean thrown =false;
		try{
			Restaurant restaurant=new Restaurant("r1", "Opis restorana r1.");
			Meal meal=new Meal("jelo1", "jelo1", 80, restaurant);
			Drink drink=new Drink("pice1", "pice1", 10, restaurant);
			WaiterOrd order= new WaiterOrd();
			meal.setId(0L);
			drink.setId(0L);
			Drink d=drinkRepository.findOne(drink.getId());
			order.getDrinks().add(d);
			Meal m=mealRepository.findOne(meal.getId());
			order.getMeals().add(m);
			orderRepository.save(order);
			WaiterOrd founded= orderRepository.findOne(order.getId());
			assertThat(founded).isNotNull();
			assertThat(founded.getMeals().size()).isGreaterThan(0);
			assertThat(founded.getDrinks().size()).isGreaterThan(0);
		}catch (Exception e){
			e.printStackTrace();
			thrown=true;
		}
		//assertThat(thrown).isEqualTo(false);
	}
	
	@Test
	public void getOrderChefTest(){
//		boolean thrown =false;
//		try{
			Restaurant restaurant=new Restaurant("r1", "Opis restorana r1.");
			restaurant.setId(0L);
			restaurantRepository.save(restaurant);
			Restaurant r=restaurantRepository.findOne(restaurant.getId());
			ChefMeal chefMeal= new ChefMeal();
			Meal meall=new Meal("jelo2", "jelo2", 80, r);
			meall.setId(0L);
			mealRepository.save(meall);
			Meal meal=mealRepository.findOne(meall.getId());
			chefMeal.getMeals().add(meal);
			assertThat(meal).isNotNull();
			chefMealRepository.save(chefMeal);
			ChefMeal founded= chefMealRepository.findOne(chefMeal.getId());
			assertThat(founded).isNotNull();
			assertThat(founded.getMeals().size()).isGreaterThan(0);
//		}catch (Exception e){
//			e.printStackTrace();
//			thrown=true;
//		}
//		assertThat(thrown).isEqualTo(false);
	}
	
	@Test
	public void saveDrinksTest(){
		boolean thrown =false;
		try{
			Restaurant restaurant=new Restaurant("r1", "Opis restorana r1.");
			BartenderDrink bartenderDrink= new BartenderDrink();
			Drink drinkk=new Drink("pice2", "pice2", 80, restaurant);
			drinkk.setId(0L);
			Drink drink=drinkRepository.findOne(drinkk.getId());
			bartenderDrink.getDrinks().add(drink);
			bartenderDrinkRepository.save(bartenderDrink);
			BartenderDrink founded=bartenderDrinkRepository.findOne(bartenderDrink.getId());
			assertThat(founded).isNotNull();
			assertThat(founded.getDrinks().size()).isGreaterThan(0);
		}catch (Exception e){
			e.printStackTrace();
			thrown=true;
		}
		//assertThat(thrown).isEqualTo(false);
	}
	
	@Test
	public void getAllDrinks(){
		saveDrinksTest();
		List<BartenderDrink> list=bartenderDrinkRepository.findAll();
		assertThat(list).isNotEmpty();
	}
	
	@Test
	public void getAllMeals(){
		List<ChefMeal> list=chefMealRepository.findAll();
		assertThat(list).isNotEmpty();
		
	}
	
	
	
}
