package mrs.app.controller;

import mrs.app.domain.restaurant.Drink;
import mrs.app.domain.restaurant.Meal;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.repository.RestaurantRepository;
import mrs.app.security.JwtTokenUtil;
import mrs.app.service.RestaurantService;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.fasterxml.jackson.databind.ObjectMapper;

@SpringBootTest
@RunWith(SpringRunner.class)
@WebAppConfiguration
@ActiveProfiles(value = "test")
public class RestaurantControllerTest {
	
	@Autowired
    private WebApplicationContext context;

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private RestaurantService restaurantService;

    private MockMvc mvc;
    
    Restaurant r;
    ObjectMapper om = new ObjectMapper();
    
    
    @Value("${jwt.header}")
    private String tokenHeader;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private UserDetailsService userDetailsService;
   
    String tok;
    
    @Before
    public void setUp()
    {
    	
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        
        r = restaurantRepository.save(new Restaurant("R1", "desc"));
    }
    
    @After
    public void tearDown()
    {
        this.mvc = MockMvcBuilders.webAppContextSetup(this.context).build();
        restaurantRepository.delete(r.getId());
    }
    
    @Test
    public void testAddRestaurant() throws Exception
    {
    		this.mvc.perform(post("/addrestaurant")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"name\" : \"R2\", \"description\" : \"neki\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"name\" : \"R2\", \"description\" : \"neki\"}"));

    }
    
    @Test
    public void testAddDrink() throws Exception
    {
    	Drink d = new Drink("pice","opis",230,r);
    		this.mvc.perform(post("/addDrink")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(d)))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"name\" : \"pice\", \"description\" : \"opis\", \"price\": 230}"));

    }
    
    
    @Test
    public void testAddMeal() throws Exception
    {
    	Meal m = new Meal("jelo","opis",230,r);
    		this.mvc.perform(post("/addMeal")
                .contentType(MediaType.APPLICATION_JSON)
                .content(om.writeValueAsString(m)))
                .andExpect(status().isCreated())
                .andExpect(content().json("{\"name\" : \"jelo\", \"description\" : \"opis\", \"price\": 230}"));

    }
    

}
