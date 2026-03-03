package bookingapp.cinemabookingapp.service.Implementation;

import bookingapp.cinemabookingapp.data.models.Booking;
import bookingapp.cinemabookingapp.data.models.PaymentStatus;
import bookingapp.cinemabookingapp.data.models.SeatManager;
import bookingapp.cinemabookingapp.data.models.Show;
import bookingapp.cinemabookingapp.data.repository.BookingRepository;
import bookingapp.cinemabookingapp.data.repository.ShowRepository;
import bookingapp.cinemabookingapp.dtos.request.BookShowRequest;
import bookingapp.cinemabookingapp.dtos.request.PaymentRequest;
import bookingapp.cinemabookingapp.dtos.response.BookShowResponse;
import bookingapp.cinemabookingapp.dtos.response.PaymentResponse;
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

import static org.junit.jupiter.api.Assertions.*;
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

    PaymentRequest paymentRequest;
    BookShowRequest bookShowRequest;
    Show show;
    @Autowired
    ShowRepository  showRepository;


    @BeforeEach
    void setUp() {
        mongoTemplate.getDb().drop();
        paymentRequest = new PaymentRequest();
        bookShowRequest = new BookShowRequest();

        show = new Show("show202","moviw292","3hrs", LocalTime.of(2,10), BigDecimal.valueOf(200),new SeatManager(2,1));
        showRepository.save(show);
        bookShowRequest.setShowId(show.getId());
        bookShowRequest.setSeatNumber("seat30");
        bookShowRequest.setEmail("adedortmahan@gmail.com");
        BookShowResponse bookShowResponse = bookingService.bookShow(bookShowRequest);
        paymentRequest.setBookingId(bookShowResponse.getBookingId());
        System.out.println(bookShowResponse.getBookingId());
        paymentRequest.setPrice(BigDecimal.valueOf(200));
    }

    @Test
    void testThatUserCanPayForBookedSeat() throws IOException, InterruptedException {
        PaymentResponse paymentResponse= paymentService.pay(paymentRequest);
        assertNotNull(paymentResponse);
        assertEquals("Authorization URL created",paymentResponse.getMessage());
    }

    @Test
    void testThatPaymentIsIndempotent() throws IOException, InterruptedException {
        PaymentResponse paymentResponse= paymentService.pay(paymentRequest);
        assertNotNull(paymentResponse);
        assertEquals("Authorization URL created",paymentResponse.getMessage());
        PaymentResponse paymentResponse3= paymentService.pay(paymentRequest);
        assertNotNull(paymentResponse);
        assertEquals("Duplicate Transaction Reference",paymentResponse3.getMessage());
    }

@Test
    void testThatWebhookListensToPaymentUpdate() throws IOException, InterruptedException {
    PaymentResponse paymentResponse= paymentService.pay(paymentRequest);
    assertNotNull(paymentResponse);
    assertEquals("Authorization URL created",paymentResponse.getMessage());
    Booking booking = bookingRepository.findById(paymentRequest.getBookingId())
            .orElseThrow(()-> new AssertionError("Booking not found"));
    Thread.sleep(40000);
    assertEquals(PaymentStatus.PAYMENT_SUCCESS,booking.getPaymentStatus());
}

}