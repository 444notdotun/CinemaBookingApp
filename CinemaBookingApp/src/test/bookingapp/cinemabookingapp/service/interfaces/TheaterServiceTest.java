package bookingapp.cinemabookingapp.service.interfaces;

import bookingapp.cinemabookingapp.data.models.SeatManager;
import bookingapp.cinemabookingapp.data.models.Show;
import bookingapp.cinemabookingapp.data.models.Theater;
import bookingapp.cinemabookingapp.data.models.TheaterAdmin;
import bookingapp.cinemabookingapp.data.repository.ShowRepository;
import bookingapp.cinemabookingapp.data.repository.TheaterRepository;
import bookingapp.cinemabookingapp.dtos.request.AddShowTheaterRequest;
import bookingapp.cinemabookingapp.dtos.request.CreateTheaterRequest;
import bookingapp.cinemabookingapp.dtos.response.CreateTheaterResponse;
import org.junit.jupiter.api.AutoClose;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.math.BigDecimal;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TheaterServiceTest {

    @Autowired
    MongoTemplate  mongoTemplate;
    @Autowired
    TheaterRepository  theaterRepository;
    @Autowired
    ShowRepository  showRepository;
    @Autowired
    TheaterService theaterService;
    CreateTheaterRequest createTheaterRequest;
    @Autowired
    TheaterAdminService theaterAdminService;
    AddShowTheaterRequest  addShowTheaterRequest;


    @BeforeEach
    void setUp() {
        mongoTemplate.getDb().drop();

        Theater theater = new Theater("lagos","ileaye");
        Show show = new Show("100", "","", LocalTime.of(5,56), BigDecimal.valueOf(30),new SeatManager(5,2));
        showRepository.save(show);
        theaterRepository.save(theater);
        createTheaterRequest = new CreateTheaterRequest();
        createTheaterRequest.setCity("London");
        createTheaterRequest.setName("london");
        addShowTheaterRequest  = new AddShowTheaterRequest();


    }

    @Test
    void testListOfTheaterCanBeSeen(){
        assertNotNull(theaterService.GetAllTheater());
    }


    @Test
    void testThatTheaterCanShowLlAvailableShow(){
        Show show = new Show("100", "","", LocalTime.of(5,56), BigDecimal.valueOf(30),new SeatManager(2,1));
        showRepository.save(show);
        Show show1 = new Show("101", "","", LocalTime.of(5,56), BigDecimal.valueOf(30),new SeatManager(2,1));
        showRepository.save(show1);
        Show show2 = new Show("102", "","", LocalTime.of(5,56), BigDecimal.valueOf(30),new SeatManager(2,1));
        showRepository.save(show2);
        CreateTheaterResponse createTheaterResponse = theaterAdminService.createTheater(createTheaterRequest);
        assertEquals("london theater created successfully",createTheaterResponse.getMessage());
        addShowTheaterRequest.setTheaterId(createTheaterResponse.getId());
        addShowTheaterRequest.setShowId(show1.getId());
        theaterAdminService.addShowToTheater(addShowTheaterRequest);
        addShowTheaterRequest.setShowId(show2.getId());
        theaterAdminService.addShowToTheater(addShowTheaterRequest);
        addShowTheaterRequest.setShowId(show.getId());
        theaterAdminService.addShowToTheater(addShowTheaterRequest);
        assertTrue(theaterRepository.existsByName("london"));
        assertNotNull(theaterService.getAllShows(createTheaterResponse.getId()));
        System.out.println(theaterService.getAllShows(createTheaterResponse.getId()));
    }


}