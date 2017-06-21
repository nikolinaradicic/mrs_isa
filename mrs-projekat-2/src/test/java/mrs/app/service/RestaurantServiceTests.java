package mrs.app.service;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collection;

import mrs.app.domain.restaurant.Drink;
import mrs.app.domain.restaurant.Meal;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.repository.DrinkRepository;
import mrs.app.repository.MealRepository;
import mrs.app.repository.RestaurantRepository;
import mrs.app.service.RestaurantService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RestaurantServiceTests {
	
	@Autowired
	RestaurantService restaurantService;
	
	@Autowired
	RestaurantRepository repository;
	
	@Autowired
	MealRepository mealRepository;
	
	@Autowired
	DrinkRepository drinkRepository;
	
	
	Restaurant restaurant;
	Drink oneDrink;
	Meal oneMeal;
	
	@Before
	public void setUp(){
		restaurant = repository.save(new Restaurant("naziv", "opis"));
		//oneDrink = drinkRepository.save(new Drink("d1","d1",200,restaurant));
		//oneMeal = mealRepository.save(new Meal("m1","m1",400,restaurant));
	}
	
	@After
	public void tearDown(){
		//drinkRepository.delete(oneDrink);
		//mealRepository.delete(oneMeal);
		repository.delete(restaurant);;
	}
	
	@Test
	public void createRestaurantTest()
	{
		boolean thrown = false;
		try {
			Restaurant created = restaurantService.create(new Restaurant("ime", "opis restorana"));
			assertThat(created.getDescription()).isEqualTo("opis restorana");
			assertThat(created.getName()).isEqualTo("ime");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			thrown = true;
		}
		assertThat(thrown).isEqualTo(false);
	}
	
	@Test
	public void createRestaurantExceptionTest()
	{
		boolean thrown = false;
		try {
			Restaurant newRestaurant = new Restaurant("bla", "bla");
			newRestaurant.setId(2L);
			restaurantService.create(newRestaurant);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			thrown = true;
		}
		assertThat(thrown).isEqualTo(true);
	}
	
	@Test
	public void findAllTest(){
		Collection<Restaurant> restaurants = restaurantService.findAll();
		assertThat(restaurants.size()).isGreaterThan(0);
	}
	
	@Test
	public void changeInformationTest(){
		restaurant.setDescription("novi opis");
		restaurant.setName("novo ime");
		int updated = restaurantService.changeInformation(restaurant);
		assertThat(updated).isEqualTo(1);
	}
	
	@Test
	public void addDrinkTest(){
		Drink drink = new Drink("naziv", "opis", 200.0, restaurant);
		try {
			restaurantService.addDrink(drink);
			Collection<Drink> drinks = restaurant.getDrinkList();
			assertThat(drinks.size()).isGreaterThan(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
//	public void updateDrinkTest(){
//		Drink drink = new Drink();
//		drink.setDescription("opis");
//		drink.setId(oneDrink.getId());
//		drink.setName("ime");
//		drink.setPrice(200);
//		restaurantService.updateDrink(drink);
//		Drink saved = drinkRepository.findOne(oneDrink.getId());
//		assertThat(saved.getDescription()).isEqualTo("opis");
//		assertThat(saved.getName()).isEqualTo("ime");
//		assertThat(saved.getPrice()).isEqualTo(200);
//	}
	
	@Test
	public void addDrinkExceptionTest(){
		Drink drink = new Drink("naziv", "opis", 200.0, restaurant);
		drink.setId(1L);
		boolean thrown = false;
		try {
			restaurantService.addDrink(drink);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			thrown = true;
		}
		assertThat(thrown).isEqualTo(true);
	}
	
	@Test
	public void addMealTest(){
		Meal meal = new Meal("naziv", "opis", 200.0, restaurant);
		try {
			restaurantService.addMeal(meal);
			Collection<Meal> menu = restaurant.getMenu();
			assertThat(menu.size()).isGreaterThan(0);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
//	@Test
//	public void updateMealTest(){
//		Meal meal = new Meal();
//		meal.setDescription("opis");
//		meal.setId(oneMeal.getId());
//		meal.setName("ime");
//		meal.setPrice(200);
//		restaurantService.updateMeal(meal);
//		Meal saved = mealRepository.findOne(oneMeal.getId());
//		assertThat(saved.getDescription()).isEqualTo("opis");
//		assertThat(saved.getName()).isEqualTo("ime");
//		assertThat(saved.getPrice()).isEqualTo(200);
//	}
	
	@Test
	public void addMealExceptionTest(){
		Meal meal = new Meal("naziv", "opis", 200.0, restaurant);
		meal.setId(1L);
		boolean thrown = false;
		try {
			restaurantService.addMeal(meal);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			thrown = true;
		}
		assertThat(thrown).isEqualTo(true);
	}

}
