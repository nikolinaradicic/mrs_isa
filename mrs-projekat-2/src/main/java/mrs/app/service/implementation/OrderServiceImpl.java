package mrs.app.service.implementation;

import java.util.Collection;
import java.util.Set;

import mrs.app.domain.restaurant.Drink;
import mrs.app.domain.restaurant.Meal;
import mrs.app.domain.restaurant.Order;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.repository.OrderRepository;
import mrs.app.service.OrderService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderServiceImpl implements OrderService{

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private OrderRepository orderRepository;

	@Override
	public Collection<Order> findAll() {
		// TODO Auto-generated method stub
		return orderRepository.findAll();
	}

	@Override
	public Order setOrder(Collection<Meal> meals, Collection<Drink> drinks,
			Restaurant r) {
		// TODO Auto-generated method stub
        Order order= new Order();
       	order.setDrinks((Set<Drink>) drinks);
       	order.setMeals((Set<Meal>) meals);
       	return orderRepository.save(order);
	}
	
}
