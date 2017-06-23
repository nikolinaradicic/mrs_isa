package mrs.app.controller;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

import mrs.app.DTOs.WorkingShiftDTO;
import mrs.app.domain.Bartender;
import mrs.app.domain.Chef;
import mrs.app.domain.Employee;
import mrs.app.domain.RestaurantManager;
import mrs.app.domain.UserType;
import mrs.app.domain.Waiter;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.Segment;
import mrs.app.domain.restaurant.Shift;
import mrs.app.domain.restaurant.WorkingShift;
import mrs.app.security.JwtTokenUtil;
import mrs.app.service.RestaurantService;
import mrs.app.service.SegmentService;
import mrs.app.service.ShiftService;
import mrs.app.service.UserService;
import mrs.app.service.WorkingShiftService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ShiftController {


	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ShiftService shiftSetvice;
	
	@Autowired
	private WorkingShiftService workingShiftService;
	

	@Autowired
	private SegmentService segmentService;
	
	@Autowired
    private JwtTokenUtil jwtTokenUtil;
	
	@Value("${jwt.header}")
    private String tokenHeader;
	
	@Autowired
    private UserService userService;
	
	@Autowired
    private RestaurantService restaurantService;
	
	@RequestMapping(
			value = "/getShifts",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<Collection<Shift>> getShifts(HttpServletRequest request) {
		logger.info("> get shifts");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        RestaurantManager saved = (RestaurantManager) userService.findByUsername(username);
		Collection<Shift> shifts = shiftSetvice.findShifts(saved.getRestaurant());
		logger.info("< get shifts");
		return new ResponseEntity<Collection<Shift>>(shifts,HttpStatus.OK);
	}
	
	
	@RequestMapping(
			value = "/addShift",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<Shift> addShift(@RequestBody Shift shift, HttpServletRequest request) throws Exception {
		logger.info("> add shift");
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        RestaurantManager saved = (RestaurantManager) userService.findByUsername(username);
        shift.setRestaurant(saved.getRestaurant());
		Shift savedShift = shiftSetvice.create(shift);
		logger.info("< add shift");
		return new ResponseEntity<Shift>(savedShift,HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/getWorkingShifts",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Collection<WorkingShift>> getWorkingShifts(@RequestBody WorkingShiftDTO shift, HttpServletRequest request){
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        RestaurantManager current = (RestaurantManager) userService.findByUsername(username);
		Collection<WorkingShift> saved = workingShiftService.findForFilter(current.getRestaurant(), shift.getStart(), shift.getEnd());
		return new ResponseEntity<Collection<WorkingShift>>(saved, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/addWorkingShift",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<WorkingShift> addWorkingShift(@RequestBody WorkingShift workingShift, HttpServletRequest request){
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        RestaurantManager saved = (RestaurantManager) userService.findByUsername(username);
		Employee employee = (Employee) userService.findEmployee(workingShift.getEmployee().getEmail());
		Shift shift = shiftSetvice.findByNameAndRestaurant(workingShift.getShift().getName(), employee.getRestaurant());
		workingShift.setRestaurant(saved.getRestaurant());
		workingShift.setEmployee(employee);
		workingShift.setShift(shift);
		if(employee.getRole() == UserType.ROLE_WAITER){
			if(workingShift.getSegment() == null){
				return new ResponseEntity<WorkingShift>(HttpStatus.NOT_FOUND);
			}
			Segment assigned = segmentService.findSegment(workingShift.getSegment().getName(), saved.getRestaurant());
			
			workingShift.setSegment(assigned);
		}
		WorkingShift savedShift = workingShiftService.create(workingShift);
		return new ResponseEntity<WorkingShift>(savedShift, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/updateWorkingShift",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<WorkingShift> updateWorkingShift(@RequestBody WorkingShift workingShift){
		WorkingShift savedShift = workingShiftService.updateDate(workingShift);
		return new ResponseEntity<WorkingShift>(savedShift, HttpStatus.OK);
	}
	
	@RequestMapping(
			value = "/deleteWorkingShift",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<Boolean> deleteWorkingShift(@RequestBody WorkingShift workingShift){
		boolean deleted = workingShiftService.delete(workingShift);
		if(deleted)
			return new ResponseEntity<Boolean>(true, HttpStatus.OK);
		else
			return new ResponseEntity<Boolean>(HttpStatus.NOT_FOUND);
	}
	
	
	@RequestMapping(
			value = "/getAvailableEmployees",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<Collection<Employee>> getEmployees(@RequestBody WorkingShift ws, HttpServletRequest request){
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        RestaurantManager saved = (RestaurantManager) userService.findByUsername(username);
		Collection<Employee> employees = userService.findEmployees(saved.getRestaurant());
		Shift shift = shiftSetvice.findByNameAndRestaurant(ws.getShift().getName(), saved.getRestaurant());
		Collection<WorkingShift> wshifts = workingShiftService.findByDateShiftRestaurant(ws.getDate(), shift, saved.getRestaurant());
		System.out.println(wshifts.size());
		for(WorkingShift s : wshifts){
			employees.remove(s.getEmployee());
		}
		return new ResponseEntity<Collection<Employee>>(employees, HttpStatus.OK);
	}

	@RequestMapping(
			value = "/getAvailableSegments",
			method = RequestMethod.POST,
			consumes = MediaType.APPLICATION_JSON_VALUE,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('RESTAURANT_MANAGER')")
	public ResponseEntity<Collection<Segment>> getSegments(@RequestBody WorkingShift ws, HttpServletRequest request){
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        RestaurantManager saved = (RestaurantManager) userService.findByUsername(username);
		Collection<Segment> segments = segmentService.findForRestaurant(saved.getRestaurant());
		Shift shift = shiftSetvice.findByNameAndRestaurant(ws.getShift().getName(), saved.getRestaurant());
		Collection<WorkingShift> wshifts = workingShiftService.findByDateShiftRestaurant(ws.getDate(), shift, saved.getRestaurant());
		for(WorkingShift s : wshifts){
			if(s.getSegment() != null){
				segments.remove(s.getSegment());
			}
		}
		return new ResponseEntity<Collection<Segment>>(segments, HttpStatus.OK);
	}

	
	@RequestMapping(
			value = "/getWorkingShiftChef",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('CHEF')")
	public ResponseEntity<WorkingShift> getWorkingShiftChef(HttpServletRequest request) throws ParseException{
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Chef current = (Chef) userService.findByUsername(username);
        Calendar now = Calendar.getInstance();
        logger.info(">started here");
        System.out.println(now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE));
        
        Restaurant restaurant= restaurantService.findOne(current.getRestaurant().getId());
        Collection<Shift> shifts=shiftSetvice.findShifts(restaurant);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat sdf1=new SimpleDateFormat("HH:MM");
        SimpleDateFormat sdf2=new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date trenutnoVreme=new Date();
        Shift smena=new Shift();
        boolean found=false;
        for(Shift sh:shifts){
            String trenutniDatum1=sdf.format(trenutnoVreme);
            String trenutniDatum2=sdf.format(trenutnoVreme);
        	trenutniDatum1+=" "+sh.getStartTime();
        	trenutniDatum2+=" "+sh.getEndTime();
        	Date datumSmenePocetak=sdf2.parse(trenutniDatum1);
        	Date datumSmeneKraj=sdf2.parse(trenutniDatum2);
        	if(trenutnoVreme.before(datumSmeneKraj) && trenutnoVreme.after(datumSmenePocetak)){
        		System.err.println("dobro je");
        		smena=sh;
        		found=true;
        		break;
        	}
        }
        if(!found){
        	return new ResponseEntity<WorkingShift>(HttpStatus.NOT_FOUND);
        }else{

        String trenutni=sdf.format(trenutnoVreme);
		WorkingShift saved = workingShiftService.findShiftForChef(current,trenutni,smena);
		System.err.println(saved);
		return new ResponseEntity<WorkingShift>(saved, HttpStatus.OK);
        }
	}
	
	
	@RequestMapping(
			value = "/getWorkingShiftWaiter",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('WAITER')")
	public ResponseEntity<WorkingShift> getWorkingShiftWaiter(HttpServletRequest request) throws ParseException{
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Waiter current = (Waiter) userService.findByUsername(username);
        Calendar now = Calendar.getInstance();
        logger.info(">started here");
        System.out.println(now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE));
        
        Restaurant restaurant= restaurantService.findOne(current.getRestaurant().getId());
        Collection<Shift> shifts=shiftSetvice.findShifts(restaurant);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat sdf1=new SimpleDateFormat("HH:MM");
        SimpleDateFormat sdf2=new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date trenutnoVreme=new Date();
        Shift smena=new Shift();
        boolean found=false;
        for(Shift sh:shifts){
            String trenutniDatum1=sdf.format(trenutnoVreme);
            String trenutniDatum2=sdf.format(trenutnoVreme);
        	trenutniDatum1+=" "+sh.getStartTime();
        	trenutniDatum2+=" "+sh.getEndTime();
        	Date datumSmenePocetak=sdf2.parse(trenutniDatum1);
        	Date datumSmeneKraj=sdf2.parse(trenutniDatum2);
        	if(trenutnoVreme.before(datumSmeneKraj) && trenutnoVreme.after(datumSmenePocetak)){
        		System.err.println("dobro je");
        		smena=sh;
        		found=true;
        		break;
        	}
        }
        if(!found){
        	return new ResponseEntity<WorkingShift>(HttpStatus.NOT_FOUND);
        }else{


        String trenutni=sdf.format(trenutnoVreme);
		WorkingShift saved = workingShiftService.findShiftForWaiter(current,trenutni,smena);
		System.err.println(saved);
		return new ResponseEntity<WorkingShift>(saved, HttpStatus.OK);
        }
	}
	
	
	@RequestMapping(
			value = "/getWorkingShiftBartender",
			method = RequestMethod.GET,
			produces = MediaType.APPLICATION_JSON_VALUE)
	@PreAuthorize("hasRole('BARTENDER')")
	public ResponseEntity<WorkingShift> getWorkingShiftBartender(HttpServletRequest request) throws ParseException{
		String token = request.getHeader(tokenHeader);
        String username = jwtTokenUtil.getUsernameFromToken(token);
        Bartender current = (Bartender) userService.findByUsername(username);
        Calendar now = Calendar.getInstance();
        logger.info(">started here");
        System.out.println(now.get(Calendar.HOUR_OF_DAY) + ":" + now.get(Calendar.MINUTE));
        
        Restaurant restaurant= restaurantService.findOne(current.getRestaurant().getId());
        Collection<Shift> shifts=shiftSetvice.findShifts(restaurant);
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        SimpleDateFormat sdf1=new SimpleDateFormat("HH:MM");
        SimpleDateFormat sdf2=new SimpleDateFormat("dd.MM.yyyy HH:mm");
        Date trenutnoVreme=new Date();
        Shift smena=new Shift();
        boolean found=false;
        for(Shift sh:shifts){
            String trenutniDatum1=sdf.format(trenutnoVreme);
            String trenutniDatum2=sdf.format(trenutnoVreme);
        	trenutniDatum1+=" "+sh.getStartTime();
        	trenutniDatum2+=" "+sh.getEndTime();
        	Date datumSmenePocetak=sdf2.parse(trenutniDatum1);
        	Date datumSmeneKraj=sdf2.parse(trenutniDatum2);
        	if(trenutnoVreme.before(datumSmeneKraj) && trenutnoVreme.after(datumSmenePocetak)){
        		System.err.println("dobro je");
        		smena=sh;
        		found=true;
        		break;
        	}
        }
        if(!found){
        	return new ResponseEntity<WorkingShift>(HttpStatus.NOT_FOUND);
        }else{

        String trenutni=sdf.format(trenutnoVreme);
		WorkingShift saved = workingShiftService.findShiftForBartender(current,trenutni,smena);
		System.err.println(saved);
		return new ResponseEntity<WorkingShift>(saved, HttpStatus.OK);
        }
	}
	
	
	
}
