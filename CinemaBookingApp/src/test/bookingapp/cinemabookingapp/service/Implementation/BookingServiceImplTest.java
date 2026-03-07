package bookingapp.cinemabookingapp.service.Implementation;

import bookingapp.cinemabookingapp.data.models.*;
import bookingapp.cinemabookingapp.data.repository.BookingRepository;
import bookingapp.cinemabookingapp.data.repository.MovieRepo;
import bookingapp.cinemabookingapp.data.repository.ShowRepository;
import bookingapp.cinemabookingapp.dtos.request.BookShowRequest;
import bookingapp.cinemabookingapp.dtos.request.PaymentRequest;
import bookingapp.cinemabookingapp.dtos.response.BookShowResponse;
import bookingapp.cinemabookingapp.dtos.response.Receipt;
import bookingapp.cinemabookingapp.service.interfaces.BookingService;
import bookingapp.cinemabookingapp.service.interfaces.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class BookingServiceImplTest {
    @Autowired
    BookingService bookingService;
    @Autowired
    MongoTemplate  mongoTemplate;
    Show show;
    @Autowired
    ShowRepository  showRepository;

    @Autowired
    BookingRepository  bookingRepository;
    @Autowired
    MovieRepo movieRepo;
    @Autowired
    PaymentService paymentService;

    PaymentRequest paymentRequest;



    BookShowRequest bookShowRequest;


    @BeforeEach
    void setUp() {
        mongoTemplate.getDb().drop();
        bookShowRequest =  new BookShowRequest();
        mongoTemplate.dropCollection(Booking.class);
        bookShowRequest.setEmail("adedortmahan@gmail.com");
        Movies movies = new Movies("movie123","","","","",0,"");
        movieRepo.save(movies);
        show = new Show("show202",movies.getMovieId(),"3hrs", LocalTime.of(2,10), BigDecimal.valueOf(200),new SeatManager(2,1));
        showRepository.save(show);
        paymentRequest = new PaymentRequest();
        paymentRequest.setPrice(BigDecimal.valueOf(190000));

    }

    @Test
    void testUserCanBookAShow(){
        bookShowRequest.setShowId(show.getId());
        bookShowRequest.setSeatNumber("seat30");
        BookShowResponse bookShowResponse = bookingService.bookShow(bookShowRequest);
        assertEquals("Hi adedortmahan@gmail.com YOUR SEAT IS RESERVED FOR THE NEXT 10 MINUTES, HEAD TO PAYMENT PLATFORM TO SECURE YOUR SEAT PERMANENTLY",bookShowResponse.getMessage());
        assertEquals(SeatStatus.LOCKED,showRepository.findById(show.getId()).get().getSeatManagerId().getSeats().get("seat30").getSeatStatus());
    }


    @Test
    void testThatBookedSeatReturnedUnbookedAfter10minuteIfPayementIsPending() throws InterruptedException {
        bookShowRequest.setShowId(show.getId());
        bookShowRequest.setSeatNumber("seat30");
        BookShowResponse bookShowResponse = bookingService.bookShow(bookShowRequest);
        assertEquals("Hi adedortmahan@gmail.com YOUR SEAT IS RESERVED FOR THE NEXT 10 MINUTES, HEAD TO PAYMENT PLATFORM TO SECURE YOUR SEAT PERMANENTLY",bookShowResponse.getMessage());
        assertEquals(SeatStatus.LOCKED,showRepository.findById(show.getId()).get().getSeatManagerId().getSeats().get("seat30").getSeatStatus());
        Thread.sleep(660000);
        assertEquals(SeatStatus.OPEN,showRepository.findById(show.getId()).get().getSeatManagerId().getSeats().get("seat30").getSeatStatus());
    }

    @Test
    void testThatBookedSeatReturnedBookedAfter10minuteIfPayementIsSuccessful() throws InterruptedException, IOException {
        bookShowRequest.setShowId(show.getId());
        bookShowRequest.setSeatNumber("seat30");
        BookShowResponse bookShowResponse = bookingService.bookShow(bookShowRequest);
        assertEquals("Hi adedortmahan@gmail.com YOUR SEAT IS RESERVED FOR THE NEXT 10 MINUTES, HEAD TO PAYMENT PLATFORM TO SECURE YOUR SEAT PERMANENTLY",bookShowResponse.getMessage());
        assertEquals(SeatStatus.LOCKED,showRepository.findById(show.getId()).get().getSeatManagerId().getSeats().get("seat30").getSeatStatus());
        Optional<Booking> booking = bookingRepository.findById(bookShowResponse.getBookingId());

        paymentService.pay(booking.get().getId());
        Thread.sleep(660000);
        assertEquals(SeatStatus.LOCKED,showRepository.findById(show.getId()).get().getSeatManagerId().getSeats().get("seat30").getSeatStatus());
    }


    @Test
    void testThatReceiptCanBeGenerated(){
        bookShowRequest.setShowId(show.getId());
        bookShowRequest.setSeatNumber("seat30");
        BookShowResponse bookShowResponse = bookingService.bookShow(bookShowRequest);
        Optional<Booking> booking =bookingRepository.findById(bookShowResponse.getBookingId());
        Receipt receipt = bookingService.generateReceipt(booking.get().getId());
       assertNotNull(receipt);
       IO.println(receipt);

    }






}