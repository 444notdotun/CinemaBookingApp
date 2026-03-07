package bookingapp.cinemabookingapp.service.Implementation;

import bookingapp.cinemabookingapp.data.models.Booking;
import bookingapp.cinemabookingapp.data.models.Movies;
import bookingapp.cinemabookingapp.data.models.Show;
import bookingapp.cinemabookingapp.data.repository.BookingRepository;
import bookingapp.cinemabookingapp.data.repository.MovieRepo;
import bookingapp.cinemabookingapp.data.repository.ShowRepository;
import bookingapp.cinemabookingapp.dtos.request.SendEmailRequest;
import bookingapp.cinemabookingapp.exceptions.BookingNotFound;
import bookingapp.cinemabookingapp.exceptions.MovieNotFoundException;
import bookingapp.cinemabookingapp.exceptions.ShowNotFoundException;
import bookingapp.cinemabookingapp.service.interfaces.BookingService;
import bookingapp.cinemabookingapp.service.interfaces.EmailServices;
import bookingapp.cinemabookingapp.utils.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Slf4j
public class EmailServiceimpl implements EmailServices {
    @Autowired
    JavaMailSender  javaMailSender;

    @Autowired
    BookingRepository  bookingRepository;
    @Autowired
    MovieRepo         movieRepo;
    @Autowired
    ShowRepository  showRepo;
    @Transactional
    @Override
    @Async
    public void sendTicketEmail(SendEmailRequest sendEmailRequest) {
       SimpleMailMessage simpleMailMessage= Mapper.sendEmail(sendEmailRequest);
        Booking booking = bookingRepository.findById(sendEmailRequest.getBookingId())
                .orElseThrow(()-> new BookingNotFound("booking not found"));

        Show show = showRepo.findById(booking.getShowId())
                .orElseThrow(()-> new ShowNotFoundException("SHOW CAN NOT BE FOUND"));
        IO.println(sendEmailRequest.getEmail());
        Movies movies = movieRepo.findMovieByMovieId(show.getMoviesId())
                .orElseThrow(()-> new MovieNotFoundException("MOVIE CANNOT BE FOUND"));

        simpleMailMessage.setSubject("Ticket Email For "+movies.getTitle());
        simpleMailMessage.setText(generateReceipt(sendEmailRequest,movies));
        log.info("Sending mail to "+sendEmailRequest.getEmail());
        javaMailSender.send(simpleMailMessage);
    }

    private String generateReceipt( SendEmailRequest sendEmailRequest,Movies movies) {
        Booking booking = bookingRepository.findById(sendEmailRequest.getBookingId())
                .orElseThrow(()-> new BookingNotFound("booking not found"));
        return "🎬 BOOKING CONFIRMED!\n\n" +
                "Movie: " + movies.getTitle() + "\n" +
                "Show ID: " + booking.getShowId() + "\n" +
                "Payment ID: " + booking.getPaymentId() + "\n" +
                "Amount Paid: ₦" + booking.getBookingPrice() + "\n" +
                "Booked At: " + booking.getBookedAt() + "\n" +
                "Payment Time: " + booking.getPaymentTime() + "\n\n" +
                "Please show this email at the cinema entrance.\n" +
                "Enjoy your movie! 🍿";

    }
}
