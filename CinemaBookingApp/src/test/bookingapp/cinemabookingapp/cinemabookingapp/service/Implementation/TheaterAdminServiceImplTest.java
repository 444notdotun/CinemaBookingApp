package bookingapp.cinemabookingapp.cinemabookingapp.service.Implementation;

import bookingapp.cinemabookingapp.data.models.Movies;
import bookingapp.cinemabookingapp.data.models.Show;
import bookingapp.cinemabookingapp.data.models.Theater;
import bookingapp.cinemabookingapp.data.repository.MovieRepo;
import bookingapp.cinemabookingapp.data.repository.ShowRepository;
import bookingapp.cinemabookingapp.data.repository.TheaterRepository;
import bookingapp.cinemabookingapp.dtos.request.*;
import bookingapp.cinemabookingapp.dtos.response.CreateShowResponse;
import bookingapp.cinemabookingapp.dtos.response.CreateTheaterResponse;
import bookingapp.cinemabookingapp.service.interfaces.TheaterAdminService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.math.BigDecimal;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class TheaterAdminServiceImplTest {

    @Autowired
    TheaterAdminService  theaterAdminService;
    CreateTheaterRequest  createTheaterRequest;
    CreateShowManagerRequest createShowManagerRequest;
    CreateMovieRequest createMovieRequest;
    AddShowTheaterRequest  addShowTheaterRequest;
    @Autowired
    TheaterRepository  theaterRepository;
    @Autowired
    ShowRepository  showRepository;

    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    MovieRepo movieRepo;

    @BeforeEach
    void setUp() {
        mongoTemplate.getDb().drop();
        createTheaterRequest = new CreateTheaterRequest();
        createTheaterRequest.setCity("London");
        createTheaterRequest.setName("london");
        createShowManagerRequest = new CreateShowManagerRequest();
        createShowManagerRequest.setStartTime(LocalTime.of(10,32));
        createShowManagerRequest.setDuration("2hours");
        createShowManagerRequest.setPrice(BigDecimal.valueOf(2000));
        createMovieRequest = new CreateMovieRequest();
        createMovieRequest.setDescription("about the avengers civil war, filled with drama and the very best");
        createMovieRequest.setTitle("The Avengers Civil War");
        createMovieRequest.setDirector("notdotun");
        createMovieRequest.setRating(5);
        createMovieRequest.setPosterUrl("https://www.london.com");
        addShowTheaterRequest =  new AddShowTheaterRequest();

    }


    @Test
    void TheaterAdminCanCreateTheater() {
        assertEquals("london theater created successfully",theaterAdminService.createTheater(createTheaterRequest).getMessage());
        assertTrue(theaterRepository.existsByName("london"));

    }
    @Test
    void TheaterAdminCanCreateMultipleTheatersWithDifferentName() {
        TheaterAdminCanCreateTheater();
        createTheaterRequest.setName("nigeria");
        createTheaterRequest.setCity("nigeria");
        assertEquals("nigeria theater created successfully",theaterAdminService.createTheater(createTheaterRequest).getMessage());
    }

    @Test
    void TheaterAdminCanCreateShow(){
        Movies movies = new Movies("676","avengers","avengersin war","nolan","4hrs",5.0,"url");
        movieRepo.save(movies);
        createShowManagerRequest.setMoviesId(movies.getMovieId());
        assertEquals("Show created successfully",theaterAdminService.createShow(createShowManagerRequest).getMessage());
    }
    @Test
    void testThatShowCanNotBeCreatedWithoutMoviesId(){
        assertThrows(RuntimeException.class, () ->theaterAdminService.createShow(createShowManagerRequest));
    }
    @Test
    void testThatMoviesCanBeCreated(){
        assertEquals("The Avengers Civil War created successfully",theaterAdminService.createMovie(createMovieRequest));
    }

    @Test
    void testThatAdminCanAddShowToTheater(){
        Movies movies = new Movies("676","avengers","avengersin war","nolan","4hrs",5.0,"url");
        movieRepo.save(movies);
        createShowManagerRequest.setMoviesId(movies.getMovieId());
        CreateShowResponse createShowResponse =theaterAdminService.createShow(createShowManagerRequest);
        assertEquals("Show created successfully",createShowResponse.getMessage());
        addShowTheaterRequest.setShowId(createShowResponse.getId());
        CreateTheaterResponse createTheaterResponse=theaterAdminService.createTheater(createTheaterRequest);
        addShowTheaterRequest.setTheaterId(createTheaterResponse.getId());
       assertEquals("show10 added successfully", theaterAdminService.addShowToTheater(addShowTheaterRequest).getMessage());
    }

}