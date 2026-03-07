package bookingapp.cinemabookingapp.service.interfaces;

import bookingapp.cinemabookingapp.data.repository.MovieRepo;
import bookingapp.cinemabookingapp.data.repository.ShowRepository;
import bookingapp.cinemabookingapp.dtos.request.CreateMovieRequest;

import bookingapp.cinemabookingapp.dtos.request.CreateShowManagerRequest;
import bookingapp.cinemabookingapp.dtos.response.CreateShowResponse;
import bookingapp.cinemabookingapp.dtos.response.CreateMovieResponse;
import bookingapp.cinemabookingapp.dtos.response.ShowReponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.math.BigDecimal;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
@SpringBootTest
class ShowServiceTest {

    @Autowired
    TheaterAdminService  theaterAdminService;
    @Autowired
            ShowService showService;
    @Autowired
    MovieRepo  movieRepo;
    CreateShowManagerRequest  createShowManagerRequest;
    @Autowired
    ShowRepository  showRepository;

    @Autowired
    MongoTemplate  mongoTemplate;


    CreateMovieRequest  createMovieRequest;
    @BeforeEach
    void setUp() {
        mongoTemplate.getDb().drop();
        createMovieRequest = new CreateMovieRequest();
        createMovieRequest.setRating(9);
        createMovieRequest.setTitle("Roman");
        createMovieRequest.setDescription("Description about the royalemepire and how it went  down the drain");
        createMovieRequest.setDuration("2hrs");
        createMovieRequest.setPosterUrl("ybktry3j");
        createMovieRequest.setDirector("notdotun");
        createShowManagerRequest = new CreateShowManagerRequest();
        createShowManagerRequest.setPrice(BigDecimal.valueOf(200));
        createShowManagerRequest.setStartTime(LocalTime.of(4,20));
        createShowManagerRequest.setSeatManagerId("");
        createShowManagerRequest.setDuration("2hrs");
        createShowManagerRequest.setRowsOfSeats(2);
        createShowManagerRequest.setNoOfSeats(1);


    }



    @Test
    void testThatYouCanSeeSHowDescription() {
        CreateMovieResponse result = theaterAdminService.createMovie(createMovieRequest);
        assertTrue(movieRepo.existsMovieByMovieId(result.getId()));
        createShowManagerRequest.setMoviesId(result.getId());
        CreateShowResponse createShowResponse=theaterAdminService.createShow(createShowManagerRequest);
        assertEquals(createMovieRequest.getDescription(), showService.ShowDescription(createShowResponse.getId()).getDescription());
    }

    @Test
    void testThatYouCanSeeAShow(){
        CreateMovieResponse result = theaterAdminService.createMovie(createMovieRequest);
        assertTrue(movieRepo.existsMovieByMovieId(result.getId()));
        createShowManagerRequest.setMoviesId(result.getId());
        CreateShowResponse createShowResponse=theaterAdminService.createShow(createShowManagerRequest);
        String showId = createShowResponse.getId();
        ShowReponse showReponse =showService.getShow(showId );
        assertEquals(showRepository.findById(showId).get().getSeatManagerId(),showReponse.getSeatManagerId());
    }



}