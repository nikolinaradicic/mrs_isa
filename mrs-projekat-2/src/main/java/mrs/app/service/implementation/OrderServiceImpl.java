package mrs.app.service.implementation;

import java.util.Collection;

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
import mrs.app.service.OrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class OrderServiceImpl implements OrderService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OrderRepository orderRepository;
	
	@Autowired
	private DrinkRepository drinkRepository;

	@Autowired
	private MealRepository mealRepository;
	
	@Autowired 
	private ChefMealRepository chefMealRepository;
	
	@Autowired
	private BartenderDrinkRepository bartenderDrinkRepository;
	
	
	@Override
	public Collection<WaiterOrd> findAll() {
		// TODO Auto-generated method stub
		return orderRepository.findAll();
	}

	@Override
	public WaiterOrd setOrder(Collection<Meal> meals, Collection<Drink> drinks,
			Restaurant r) {
		// TODO Auto-generated method stub
        WaiterOrd order= new WaiterOrd();

       	return orderRepository.save(order);
	}

	@Override
	public WaiterOrd setOrderMeal(WaiterOrd order) {
		// TODO Auto-generated method stub
		System.out.println("+++++++Pica+++++++");
		for(Drink d:order.getDrinks()){
			System.out.println(d.getId());
		}
		System.out.println("+++++++Hrana++++++");
		System.out.println(order.getMeals());
		WaiterOrd ord=new WaiterOrd();
		ord.setId(0L);
		for(Drink d:order.getDrinks()){
			ord.getDrinks().add(this.drinkRepository.findOne(d.getId()));
		}
		for(Meal m:order.getMeals()){
			ord.getMeals().add(this.mealRepository.findOne(m.getId()));
		}
		return orderRepository.save(ord);
	}

	@Override
	public ChefMeal getOrderChef(ChefMeal order) {
		// TODO Auto-generated method stub
		ChefMeal ord=new ChefMeal();
		ord.setId(0L);
		for(Meal m:order.getMeals()){
			ord.getMeals().add(this.mealRepository.findOne(m.getId()));
		}
		
		return chefMealRepository.save(ord);
	}

	@Override
	public Collection<ChefMeal> getAllMeals() {

		return chefMealRepository.findAll();
	}

	@Override
	public Collection<BartenderDrink> getAllDrinks() {
		// TODO Auto-generated method stub
		return bartenderDrinkRepository.findAll();
	}

	@Override
	public BartenderDrink saveDrinks(BartenderDrink order) {
		// TODO Auto-generated method stub
		BartenderDrink ord=new BartenderDrink();
		ord.setId(0L);
		for(Drink d:order.getDrinks()){
			ord.getDrinks().add(this.drinkRepository.findOne(d.getId()));
		}
		
		return bartenderDrinkRepository.save(ord);
	}
	
}
