package mrs.app.service.implementation;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import mrs.app.DTOs.ItemDrinkDTO;
import mrs.app.DTOs.ItemMealDTO;
import mrs.app.domain.Guest;
import mrs.app.domain.Waiter;
import mrs.app.domain.restaurant.BartenderDrink;
import mrs.app.domain.restaurant.Bill;
import mrs.app.domain.restaurant.ChefMeal;
import mrs.app.domain.restaurant.Drink;
import mrs.app.domain.restaurant.ItemDrink;
import mrs.app.domain.restaurant.ItemMeal;
import mrs.app.domain.restaurant.Meal;
import mrs.app.domain.restaurant.Reservation;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.RestaurantTable;
import mrs.app.domain.restaurant.Visit;
import mrs.app.domain.restaurant.WaiterOrd;
import mrs.app.repository.BartenderDrinkRepository;
import mrs.app.repository.CheckRepository;
import mrs.app.repository.ChefMealRepository;
import mrs.app.repository.DrinkRepository;
import mrs.app.repository.ItemDrinkRepository;
import mrs.app.repository.ItemMealRepository;
import mrs.app.repository.MealRepository;
import mrs.app.repository.OrderRepository;
import mrs.app.repository.ReservationRepository;
import mrs.app.repository.RestaurantRepository;
import mrs.app.repository.RestaurantTableRepository;
import mrs.app.repository.UserRepository;
import mrs.app.repository.VisitRepository;
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
	private CheckRepository checkRepository;
	
	@Autowired
	private ItemDrinkRepository itemDrinkRepository;

	@Autowired
	private ItemMealRepository itemMealRepository;
	
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
	
	@Autowired
	private RestaurantTableRepository restTableRepository;
	
	@Autowired
	private ReservationRepository reservationRepository;
	
	@Autowired
	private VisitRepository visitRepository;
	
	@Override
	public Collection<WaiterOrd> findAll() {
		// TODO Auto-generated method stub
		return orderRepository.findAll();
	}

	@Override
	public WaiterOrd setOrderMeal(WaiterOrd order, Restaurant restaurant,Waiter waiter) {
		// TODO Auto-generated method stub
		//pravim order
		WaiterOrd ord=new WaiterOrd();
		//postavljam restoran
		ord.setRestaurant(restaurant);
		ArrayList<ItemDrink> itemDr=new ArrayList<ItemDrink>();
		ArrayList<ItemMeal> itemMe=new ArrayList<ItemMeal>();
		//konobar
		Waiter w= (Waiter) userRepository.findOne(waiter.getId());
		for(ItemDrink d:order.getDrinks()){
			//pronadjem pice koje sam poslao
			Drink dr=this.drinkRepository.findOne(d.getId());
			ItemDrink drink=new ItemDrink();
			drink.setDrink(dr);
			drink.setQuantity(d.getQuantity());
			ord.getDrinks().add(drink);
		}
		for(ItemMeal m:order.getMeals()){
			ItemMeal im=new ItemMeal();
			Meal meal=this.mealRepository.findOne(m.getId());
			im.setMeal(meal);
			im.setQuantity(m.getQuantity());

			System.out.println("jelo koje?  "+im.getMeal().getName());
			ord.getMeals().add(im);
		}
		RestaurantTable table=restTableRepository.findOne(order.getTable().getId());
		ord.setTable(table);
		ord.setWaiter(w);
		return orderRepository.save(ord);
	}

	@Override
	public ChefMeal getOrderChef(ChefMeal order, Restaurant r) {
		// TODO Auto-generated method stub
		Restaurant restaurant = restaurantRepository.getOne(r.getId());
		ChefMeal ord=new ChefMeal();
		ord.setRestaurant(restaurant);
		System.out.println("MEALS");		
		for(ItemMeal m:order.getMeals()){
			
			ItemMeal im=new ItemMeal();
			Meal meal=this.mealRepository.findOne(m.getId());
			im.setMeal(meal);
			im.setQuantity(m.getQuantity());

			System.out.println("jelo koje?  "+im.getMeal().getName());
			ord.getMeals().add(im);
		}
		return chefMealRepository.save(ord);
	}

	@Override
	public Collection<ItemMeal> getAllMeals(Restaurant restaurant) {
		Restaurant r=restaurantRepository.findOne(restaurant.getId());
		Collection<ChefMeal> cm=chefMealRepository.findByRest(r);
		
		ArrayList<ItemMeal> jela= new ArrayList<>();
		
		int counter=0;
		for(ChefMeal cf:cm){
			for(ItemMeal m: cf.getMeals()){
				counter++;
				System.out.println("Order "+counter);
				jela.add(m);
			}
		}
		return jela;
	}

	@Override
	public Collection<WaiterOrd> getAllDrinks(Restaurant restaurant) {
		// TODO Auto-generated method stub
		Restaurant r=restaurantRepository.findOne(restaurant.getId());
		Collection<WaiterOrd> bd=orderRepository.findByRest(r);
		System.out.println("orderrr "+bd.size());

		return bd;
	}

	@Override
	public BartenderDrink saveDrinks(BartenderDrink order, Restaurant r) {
		// TODO Auto-generated method stub
		Restaurant restaurant = restaurantRepository.getOne(r.getId());
		BartenderDrink ord=new BartenderDrink();
		ord.setId(0L);
		ord.setRestaurant(restaurant);
		for(ItemDrink d:order.getDrinks()){
			System.out.println(d.getId());
			Drink dr=this.drinkRepository.findOne(d.getId());
			ItemDrink drink=new ItemDrink();
			drink.setDrink(dr);
			drink.setQuantity(d.getQuantity());
			ord.getDrinks().add(drink);
		}	
		return bartenderDrinkRepository.save(ord);
	}

	@Override
	public Collection<WaiterOrd> getMyOrder(Restaurant r, Long id) {
		// TODO Auto-generated method stub
		Waiter waiter= (Waiter) userRepository.findOne(id);
		return orderRepository.findByWaiter(waiter);
	}

	@Override
	public ItemDrink updateItemDrink(ItemDrink itemDr) {
		// TODO Auto-generated method stub
		itemDrinkRepository.updateItemDrink(itemDr.getQuantity(), itemDr.getId());
		ItemDrink updatedOne=itemDrinkRepository.findOne(itemDr.getId());
		return updatedOne;
	}

	@Override
	public ItemMeal updateItemMeal(ItemMeal itemMe) {
		// TODO Auto-generated method stub
		itemMealRepository.updateItemMeal(itemMe.getQuantity(), itemMe.getId());
		ItemMeal updatedOne=itemMealRepository.findOne(itemMe.getId());
		return updatedOne;
	}

	@Override
	public ItemMeal updateItemMealStatus(ItemMeal itemMe) {
		// TODO Auto-generated method stub
		itemMealRepository.updateItemMealStatus(itemMe.getStatus(), itemMe.getId());
		ItemMeal updatedOne=itemMealRepository.findOne(itemMe.getId());
		return updatedOne;
	}

	@Override
	public ItemDrink updateItemDrinkStatus(ItemDrink itemDr) {
		// TODO Auto-generated method stub
		itemDrinkRepository.updateItemDrinkStatus(itemDr.getStatus(), itemDr.getId());
		ItemDrink updatedOne=itemDrinkRepository.findOne(itemDr.getId());
		return updatedOne;
	}


	@Override
	public void deleteItemMeal(ItemMealDTO orderDTO) {
		// TODO Auto-generated method stub
		WaiterOrd order=orderRepository.findOne(orderDTO.getIdWaiterOrd());
		ItemMeal itemMeal=itemMealRepository.findOne(orderDTO.getIdItemMeal());
		order.getMeals().remove(itemMeal);
		orderRepository.save(order);
	}

	@Override
	public void deleteItemDrink(ItemDrinkDTO orderDTO) {
		// TODO Auto-generated method stub
		WaiterOrd order=orderRepository.findOne(orderDTO.getIdWaiterOrd());
		ItemDrink itemDrink=itemDrinkRepository.findOne(orderDTO.getIdItemDrink());
		order.getDrinks().remove(itemDrink);
		orderRepository.save(order);
	}

//	@Override
//	public Bill createCheck(Bill bill) {
//		// TODO Auto-generated method stub
//		WaiterOrd order=orderRepository.findOne(bill.getOrder().getId());
//		Bill definedCheck=new Bill();
//		definedCheck.setOrder(order);
//		definedCheck.setFinal_price(bill.getFinal_price());
//		definedCheck.setId(0L);
//		return checkRepository.save(definedCheck);
//	}

	@Override
	public Visit createVisit(Bill bill) {
		// TODO Auto-generated method stub
		WaiterOrd order=orderRepository.findOne(bill.getOrder().getId());
		Bill definedCheck=new Bill();
		definedCheck.setOrder(order);
		definedCheck.setFinal_price(bill.getFinal_price());
		checkRepository.save(definedCheck);
		Visit createdVisit= new Visit();
		createdVisit.setBill(definedCheck);
		Date datum=new Date();
		createdVisit.setDate(datum);
		SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
		String datumString= sdf.format(datum);
		boolean foundReservation=false;
		Collection<Reservation> reservations=reservationRepository.getReservByDate(datumString);
		Reservation res=new Reservation();
		if(reservations!=null){
			for(Reservation r:reservations){
				for(RestaurantTable table :r.getRestaurant_table()){
					if(table.getId()==bill.getOrder().getTable().getId()){
						res=r;
						foundReservation=true;
					}
				}	
			}
		}
		if(foundReservation){
			createdVisit.setReservation(res);
		}else{
			createdVisit.setReservation(null);
		}
		return visitRepository.save(createdVisit);
	}

	@Override
	public Collection<Visit> findMyVisits(Guest guest) {
		// TODO Auto-generated method stub
		Collection<Visit> visits=visitRepository.findAll();
		ArrayList<Visit> foundedVisits=new ArrayList<Visit>();
		if(!visits.isEmpty()){
			for(Visit v:visits){
				if(v.getReservation()==null)
					continue;
				if(v.getReservation().getGuest().getId()==guest.getId())
					foundedVisits.add(v);
			}
			return foundedVisits;
		}
		return null;
	}



	
}
