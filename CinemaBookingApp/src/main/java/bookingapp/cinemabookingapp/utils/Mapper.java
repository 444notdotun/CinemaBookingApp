package bookingapp.cinemabookingapp.utils;

import bookingapp.cinemabookingapp.data.models.*;
import bookingapp.cinemabookingapp.dtos.request.*;
import bookingapp.cinemabookingapp.dtos.response.*;
import org.springframework.mail.SimpleMailMessage;

import java.math.BigDecimal;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

public class Mapper {
    public static Admin mapAdminRequestToTheaterAdmin(CreateAdminRequest createAdminRequest) {
        return new TheaterAdmin(createAdminRequest.getCity()+"Theater Admin",createAdminRequest.getPassword(), Role.THEATER_ADMIN);
    }

    public static CreateAdminResponse MapAdminToResponse(Admin admin) {
        CreateAdminResponse createAdminResponse = new CreateAdminResponse();
        createAdminResponse.setMessage(admin.getId()+" created successfully");
        createAdminResponse.setId(admin.getId());
        return createAdminResponse;
    }

    public static Theater MapTheaterRequestToTheater(CreateTheaterRequest createTheater) {
        return new Theater(createTheater.getCity(),createTheater.getName());}

    public static CreateTheaterResponse SetCreateTheaterResponse(Theater theater) {
        CreateTheaterResponse createTheaterResponse = new CreateTheaterResponse();
        createTheaterResponse.setMessage(theater.getName()+" theater created successfully");
        createTheaterResponse.setId(theater.getId());
        return createTheaterResponse;
    }


    public static AddShowToTheaterResponse MapAddShowResponse(AddShowTheaterRequest theater) {
        AddShowToTheaterResponse addShowToTheaterResponse = new AddShowToTheaterResponse();
        addShowToTheaterResponse.setMessage(theater.getShowId()+" added successfully");
        return addShowToTheaterResponse;
    }

    public static Show mapDtosToShow(CreateShowManagerRequest createShowManagerRequest) {
        SeatManager seatManager= new SeatManager(createShowManagerRequest.getNoOfSeats(),createShowManagerRequest.getRowsOfSeats());
        return new Show("", createShowManagerRequest.getMoviesId(), createShowManagerRequest.getDuration(), createShowManagerRequest.getStartTime(),createShowManagerRequest.getPrice(),seatManager);

    }

    public static CreateShowResponse mapShowToCreateShowResponse(Show show) {
        CreateShowResponse createShowResponse = new CreateShowResponse();
        createShowResponse.setMessage("Show created successfully");
        createShowResponse.setId(show.getId());
        return  createShowResponse;
    }

    public static Movies mapMovieRequestToMovie(CreateMovieRequest createMovieRequest) {
        return new Movies("",createMovieRequest.getDescription(), createMovieRequest.getTitle(), createMovieRequest.getDirector(), createMovieRequest.getDuration(), createMovieRequest.getRating(), createMovieRequest.getPosterUrl());
    }

    public static Booking MapRequestToBooking(BookShowRequest bookShowRequest) {
        Booking booking = new Booking();
        booking.setSeatNumber(bookShowRequest.getSeatNumber());
        booking.setUserName(bookShowRequest.getEmail());
        booking.setShowId(bookShowRequest.getShowId());
        return booking;
    }

    public static BookShowResponse MapBookingToResponse(Booking booking) {
        BookShowResponse bookShowResponse = new BookShowResponse();
        bookShowResponse.setBookingId(booking.getId());
        bookShowResponse.setMessage("Hi "+booking.getUserName() +" YOUR SEAT IS RESERVED FOR THE NEXT 10 MINUTES, HEAD TO PAYMENT PLATFORM TO SECURE YOUR SEAT PERMANENTLY");
        return bookShowResponse;
    }

    public static ShowReponse MapShowToShowReponse(Show show) {
        ShowReponse showReponse = new ShowReponse();
        showReponse.setDuration(show.getDuration());
        showReponse.setPrice(show.getPrice());
        showReponse.setMoviesId(show.getMoviesId());
        showReponse.setStartTime(show.getStartTime());
        showReponse.setSeatManagerId(show.getSeatManagerId());
        return showReponse;
    }

    public static Receipt  createReceipt(Booking booking, Show show, Movies movies) {
        Receipt  receipt = new Receipt();
        receipt.setBookingPrice(String.valueOf(booking.getBookingPrice()));
        receipt.setCreatedAt(LocalDateTime.now());
        receipt.setShowId(show.getId());
        receipt.setMovieName(movies.getTitle());
        receipt.setEmail(booking.getUserName());
        receipt.setPaymentId(booking.getPaymentId());
        receipt.setPaymentTime(booking.getPaymentTime());
        receipt.setPaymentStatus(booking.getPaymentStatus());
        receipt.setBookedAt(booking.getBookedAt());
        return receipt;
    }

    public static SimpleMailMessage sendEmail(SendEmailRequest sendEmailRequest) {
        SimpleMailMessage  simpleMailMessage = new SimpleMailMessage();
        simpleMailMessage.setFrom("onboarding@resend.dev");
        simpleMailMessage.setTo(sendEmailRequest.getEmail());
        return simpleMailMessage;
    }

    public static SendEmailRequest createSenderRequest(String eMail, String bookingId) {
        SendEmailRequest  sendEmailRequest = new SendEmailRequest();
        sendEmailRequest.setEmail(eMail);
        sendEmailRequest.setBookingId(bookingId);
        return sendEmailRequest;
    }

    public static PaymentRequest mapBookingToRequest(Booking booking) {
        PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setEmail(booking.getUserName());
        paymentRequest.setPrice(booking.getBookingPrice());
        paymentRequest.setReference(booking.getPaymentId());
        return paymentRequest;
    }

//    public static String mapReceiptEmail(Receipt receipt) {
//        return private String buildTicketMessage(Booking booking) {
//            return "🎬 BOOKING CONFIRMED!\n\n" +
//                    "Movie: " + booking.getMovieName() + "\n" +
//                    "Show ID: " + booking.getShowId() + "\n" +
//                    "Payment ID: " + booking.getPaymentId() + "\n" +
//                    "Amount Paid: ₦" + booking.getBookingPrice() + "\n" +
//                    "Booked At: " + booking.getBookedAt() + "\n" +
//                    "Payment Time: " + booking.getPaymentTime() + "\n\n" +
//                    "Please show this email at the cinema entrance.\n" +
//                    "Enjoy your movie! 🍿";
//        }
//    }
}
