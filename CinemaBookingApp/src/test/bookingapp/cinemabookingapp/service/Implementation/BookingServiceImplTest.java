package bookingapp.cinemabookingapp.service.Implementation;

import bookingapp.cinemabookingapp.data.models.Booking;
import bookingapp.cinemabookingapp.data.models.SeatManager;
import bookingapp.cinemabookingapp.data.models.SeatStatus;
import bookingapp.cinemabookingapp.data.models.Show;
import bookingapp.cinemabookingapp.data.repository.ShowRepository;
import bookingapp.cinemabookingapp.dtos.request.BookShowRequest;
import bookingapp.cinemabookingapp.dtos.response.BookShowResponse;
import bookingapp.cinemabookingapp.service.interfaces.BookingService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import java.math.BigDecimal;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class BookingServiceImplTest {
    @Autowired
    BookingService bookingService;
    @Autowired
    MongoTemplate  mongoTemplate;
    Show show;
    @Autowired
    ShowRepository  showRepository;

    BookShowRequest bookShowRequest;


    @BeforeEach
    void setUp() {
        bookShowRequest =  new BookShowRequest();
        mongoTemplate.dropCollection(Booking.class);
        bookShowRequest.setEmail("dotunTest");
        show = new Show("show202","moviw292","3hrs", LocalTime.of(2,10), BigDecimal.valueOf(200),new SeatManager(2,1));
        showRepository.save(show);
    }

    @Test
    void testUserCanBookAShow(){
        bookShowRequest.setShowId(show.getId());
        bookShowRequest.setSeatNumber("seat30");
        BookShowResponse bookShowResponse = bookingService.bookShow(bookShowRequest);
        assertEquals("hi dotunTest you've successfully booked successfully yourself a seat, head to the payment platform to finally secure it",bookShowResponse.getMessage());
        assertEquals(SeatStatus.LOCKED,showRepository.findById(show.getId()).get().getSeatManagerId().getSeats().get("seat30").getSeatStatus());
    }


    @Test
    void testThatBookedSeatReturnedUnbookedAfter1minuteIfPayementIsPending() throws InterruptedException {
        bookShowRequest.setShowId(show.getId());
        bookShowRequest.setSeatNumber("seat30");
        BookShowResponse bookShowResponse = bookingService.bookShow(bookShowRequest);
        assertEquals("hi dotunTest you've successfully booked successfully yourself a seat, head to the payment platform to finally secure it",bookShowResponse.getMessage());
        assertEquals(SeatStatus.LOCKED,showRepository.findById(show.getId()).get().getSeatManagerId().getSeats().get("seat30").getSeatStatus());
        Thread.sleep(6300000);
        assertEquals(SeatStatus.OPEN,showRepository.findById(show.getId()).get().getSeatManagerId().getSeats().get("seat30").getSeatStatus());
    }







}