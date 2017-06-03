package mrs.app.service.implementation;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Set;

import mrs.app.domain.Waiter;
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
import mrs.app.repository.UserRepository;
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
	
	@Autowired
	private RestaurantRepository restaurantRepository;
	
	@Autowired
	private UserRepository userRepository;
	
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
	public WaiterOrd setOrderMeal(WaiterOrd order, Restaurant restaurant,Waiter waiter) {
		// TODO Auto-generated method stub
		WaiterOrd ord=new WaiterOrd();
		ord.setId(0L);
		ord.setRestaurant(restaurant);
		Waiter w= (Waiter) userRepository.findOne(waiter.getId());
		for(Drink d:order.getDrinks()){
			ord.getDrinks().add(this.drinkRepository.findOne(d.getId()));
		}
		for(Meal m:order.getMeals()){
			ord.getMeals().add(this.mealRepository.findOne(m.getId()));
		}
		ord.setWaiter(w);
		return orderRepository.save(ord);
	}

	@Override
	public ChefMeal getOrderChef(ChefMeal order, Restaurant r) {
		// TODO Auto-generated method stub
		Restaurant restaurant = restaurantRepository.getOne(r.getId());
		ChefMeal ord=new ChefMeal();
		ord.setId(0L);
		ord.setRestaurant(restaurant);
		System.out.println("MEALS");		
		for(Meal m:order.getMeals()){
			System.out.println(m.getName());
			ord.getMeals().add(this.mealRepository.findOne(m.getId()));
		}
		return chefMealRepository.save(ord);
	}

	@Override
	public Collection<Meal> getAllMeals(Restaurant restaurant) {
		Restaurant r=restaurantRepository.findOne(restaurant.getId());
		ArrayList<WaiterOrd> listica= (ArrayList<WaiterOrd>) orderRepository.findAll();
		ArrayList<WaiterOrd> templistica= new ArrayList<WaiterOrd>();
		ArrayList<ChefMeal> lista= (ArrayList<ChefMeal>) chefMealRepository.findAll();
		ArrayList<ChefMeal> temp=(ArrayList<ChefMeal>) chefMealRepository.findAll();
		ArrayList<Meal> tempMeal= (ArrayList<Meal>) mealRepository.findAll();
		for(WaiterOrd wo : listica){
			System.out.println("wo++++ "+wo.getRestaurant().getId());
			System.out.println("rdid++++"+r.getId());
			if(wo.getRestaurant().getId()==r.getId()){
				templistica.add(wo);
			}
		}
		ArrayList<Meal> jela= new ArrayList<>();
		for(WaiterOrd wo1:templistica){
			for(Meal m:wo1.getMeals()){
				System.out.println("jelo "+m.getName());
				jela.add(m);
			}
		}
		return jela;
	}

	@Override
	public Collection<Drink> getAllDrinks(Restaurant restaurant) {
		// TODO Auto-generated method stub
		Restaurant r=restaurantRepository.findOne(restaurant.getId());
		ArrayList<WaiterOrd> listica= (ArrayList<WaiterOrd>) orderRepository.findAll();
		ArrayList<WaiterOrd> templistica= new ArrayList<WaiterOrd>();
		for(WaiterOrd wo : listica){
			System.out.println("wo++++ "+wo.getRestaurant().getId());
			System.out.println("rdid++++"+r.getId());
			if(wo.getRestaurant().getId()==r.getId()){
				templistica.add(wo);
			}
		}
		ArrayList<Drink> pica= new ArrayList<>();
		for(WaiterOrd wo1:templistica){
			for(Drink d:wo1.getDrinks()){
				System.out.println("pice "+d.getName());
				pica.add(d);
			}
		}
		return pica;
	}

	@Override
	public BartenderDrink saveDrinks(BartenderDrink order, Restaurant r) {
		// TODO Auto-generated method stub
		Restaurant restaurant = restaurantRepository.getOne(r.getId());
		BartenderDrink ord=new BartenderDrink();
		ord.setId(0L);
		ord.setRestaurant(restaurant);
		for(Drink d:order.getDrinks()){
			ord.getDrinks().add(this.drinkRepository.findOne(d.getId()));
		}	
		return bartenderDrinkRepository.save(ord);
	}

	@Override
	public Collection<WaiterOrd> getMyOrder(Restaurant r, Long id) {
		// TODO Auto-generated method stub
		Restaurant restaurant = restaurantRepository.getOne(r.getId());
		Waiter waiter= (Waiter) userRepository.findOne(id);
		ArrayList<WaiterOrd> orders=(ArrayList<WaiterOrd>) orderRepository.findAll();
		ArrayList<WaiterOrd> lista=new ArrayList();
		for(WaiterOrd wo:orders){
			if(wo.getWaiter().getId()==waiter.getId())
				lista.add(wo);
		}
		return lista;
	}
	
}
