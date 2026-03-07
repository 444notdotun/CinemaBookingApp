package bookingapp.cinemabookingapp.service.Implementation;

import bookingapp.cinemabookingapp.data.models.*;
import bookingapp.cinemabookingapp.data.repository.BookingRepository;
import bookingapp.cinemabookingapp.data.repository.MovieRepo;
import bookingapp.cinemabookingapp.data.repository.ShowRepository;
import bookingapp.cinemabookingapp.dtos.request.BookShowRequest;
import bookingapp.cinemabookingapp.dtos.request.PaymentRequest;
import bookingapp.cinemabookingapp.dtos.response.BookShowResponse;
import bookingapp.cinemabookingapp.dtos.response.PaymentResponse;
import bookingapp.cinemabookingapp.service.interfaces.BookingService;
import bookingapp.cinemabookingapp.service.interfaces.PaymentService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.*;
@Slf4j
@SpringBootTest
class PaymentServiceImplTest {
    @Autowired
    PaymentService  paymentService;
    @Autowired
    MongoTemplate  mongoTemplate;
    @Autowired
    BookingService bookingService;
    @Autowired
            BookingRepository bookingRepository;

    BookShowRequest bookShowRequest;

    BookShowResponse bookShowResponse;
    Show show;
    @Autowired
    ShowRepository  showRepository;
    @Autowired
    MovieRepo movieRepo;


    @BeforeEach
    void setUp() {
        mongoTemplate.getDb().drop();

        bookShowRequest = new BookShowRequest();
        show = new Show("show202","moviw292","3hrs", LocalTime.of(2,10), BigDecimal.valueOf(2000),new SeatManager(2,1));
        Movies movies = new Movies(show.getMoviesId(),"","stack up","notdotun",",",4,"");
        movieRepo.save(movies);
        showRepository.save(show);
        bookShowRequest.setShowId(show.getId());
        bookShowRequest.setSeatNumber("seat30");
        bookShowRequest.setEmail("adedortmahan@gmail.com");
        bookShowResponse = bookingService.bookShow(bookShowRequest);


    }

    @Test
    void testThatUserCanPayForBookedSeat() throws IOException, InterruptedException {
        PaymentResponse paymentResponse= paymentService.pay(bookShowResponse.getBookingId());
        assertNotNull(paymentResponse);
        assertEquals("Authorization URL created",paymentResponse.getMessage());
    }

    @Test
    void testThatPaymentIsIndempotent() throws IOException, InterruptedException {
        PaymentResponse paymentResponse= paymentService.pay(bookShowResponse.getBookingId());
        assertNotNull(paymentResponse);
        assertEquals("Authorization URL created",paymentResponse.getMessage());
        PaymentResponse paymentResponse3= paymentService.pay(bookShowResponse.getBookingId());
        assertNotNull(paymentResponse);
        assertEquals("Duplicate Transaction Reference",paymentResponse3.getMessage());
    }

@Test
    void testThatWebhookListensToPaymentUpdate() throws IOException, InterruptedException {
    PaymentResponse paymentResponse= paymentService.pay(bookShowResponse.getBookingId());
    assertNotNull(paymentResponse);
    assertEquals("Authorization URL created",paymentResponse.getMessage());
    Thread.sleep(70000);
    Booking booking = bookingRepository.findById(bookShowResponse.getBookingId())
            .orElseThrow(()-> new AssertionError("Booking not found"));
    assertEquals(PaymentStatus.PAYMENT_SUCCESS,booking.getPaymentStatus());
}

//@Test
//    void testThatWebhookListensToPaymentCancel() throws IOException, InterruptedException {
//    PaymentResponse paymentResponse= paymentService.pay(paymentRequest);
//    assertNotNull(paymentResponse);
//    assertEquals("Authorization URL created",paymentResponse.getMessage());
//    Thread.sleep(60000);
//    Booking booking = bookingRepository.findById(paymentRequest.getBookingId())
//            .orElseThrow(()-> new AssertionError("Booking not found"));
//    assertEquals(PaymentStatus.PAYMENT_FAILED,booking.getPaymentStatus());
//
//}

}