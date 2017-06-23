package mrs.app.service;

import static org.assertj.core.api.Assertions.assertThat;
import mrs.app.DTOs.TableDTO;
import mrs.app.domain.restaurant.Restaurant;
import mrs.app.domain.restaurant.RestaurantTable;
import mrs.app.domain.restaurant.Segment;
import mrs.app.repository.RestaurantRepository;
import mrs.app.repository.RestaurantTableRepository;
import mrs.app.repository.SegmentRepository;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles(value = "test")
public class TableServiceTests {
	
	@Autowired
	TableService tableService;
	
	@Autowired
	SegmentRepository segmentRepository;
	
	@Autowired
	RestaurantRepository restaurantRepository;
	
	@Autowired
	RestaurantTableRepository tableRepository;
	
	Segment s;
	Restaurant r;
	RestaurantTable t;
	@Before
	public void setUp(){
		r = restaurantRepository.save(new Restaurant("neki","opis"));
		s = segmentRepository.save(new Segment("naziv", r));
		t = new RestaurantTable();
		t.setChairNumber(2);
		t.setName("1234");
		t.setSegment(s);
		t = tableRepository.save(t);
	}
	
	@After
	public void tearDown(){
		restaurantRepository.delete(r.getId());
	}

	@Test
	public void createTableTest()
	{
		boolean thrown = false;
		try {
			TableDTO dt = new TableDTO(4, "123");
			RestaurantTable created = tableService.create(new RestaurantTable(dt,s));
			assertThat(created.getName()).isEqualTo("123");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			thrown = true;
		}
		assertThat(thrown).isEqualTo(false);
	}
	
	@Test
	public void createTableExceptionTest()
	{
		boolean thrown = false;
		try {
			RestaurantTable newTable = new RestaurantTable();
			newTable.setId(2L);
			tableService.create(newTable);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			thrown = true;
		}
		assertThat(thrown).isEqualTo(true);
	}
	
	@Test
	public void findByNameAndSegmentTest()
	{
		RestaurantTable rt = tableService.findByNameAndSegment("1234", s);
		assertThat(rt.getId()).isEqualTo(t.getId());
	}
}
