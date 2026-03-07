package bookingapp.cinemabookingapp.service.Implementation;

import bookingapp.cinemabookingapp.data.models.*;
import bookingapp.cinemabookingapp.data.repository.BookingRepository;
import bookingapp.cinemabookingapp.data.repository.MovieRepo;
import bookingapp.cinemabookingapp.data.repository.ShowRepository;
import bookingapp.cinemabookingapp.dtos.request.BookShowRequest;
import bookingapp.cinemabookingapp.dtos.response.BookShowResponse;
import bookingapp.cinemabookingapp.dtos.response.Receipt;
import bookingapp.cinemabookingapp.exceptions.BookingNotFound;
import bookingapp.cinemabookingapp.exceptions.MovieNotFoundException;
import bookingapp.cinemabookingapp.exceptions.SeatDetailException;
import bookingapp.cinemabookingapp.exceptions.ShowNotFoundException;
import bookingapp.cinemabookingapp.service.interfaces.BookingService;
import bookingapp.cinemabookingapp.utils.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
public class BookingServiceImpl implements BookingService {
    @Autowired
    ShowRepository showRepo;
    @Autowired
    IdGeneratorServices idGeneratorServices;
    @Autowired
    BookingRepository bookingRepo;
    @Autowired
    MovieRepo  movieRepo;

    @Override
    @Transactional
    public BookShowResponse bookShow(BookShowRequest bookShowRequest) {
        Show show = showRepo.findById(bookShowRequest.getShowId())
                .orElseThrow(()->new ShowNotFoundException("show not found"));
        SeatManager seatManager =show.getSeatManagerId();
        Seat seat = seatManager.getSeats().get(bookShowRequest.getSeatNumber());
        verifySeatStatus(seat);
        Booking booking = Mapper.MapRequestToBooking(bookShowRequest);
        booking.setBookingPrice(show.getPrice());
        seat.setSeatStatus(SeatStatus.LOCKED);
        show.setSeatManagerId(seatManager);
        showRepo.save(show);
        log.info("booking request received");
        booking.setId(idGeneratorServices.generateId("booking"));
        bookingRepo.save(booking);
        return Mapper.MapBookingToResponse(booking);
    }

    @Transactional
    @Scheduled(fixedRate = 20000)
    private  void unBook() {
        List<Booking> bookingList;
        bookingList=bookingRepo.findByPaymentStatusInAndExpirationTimeBefore(PaymentStatus.PAYMENT_PENDING, LocalDateTime.now());
        if(bookingList.isEmpty()){
            log.info("booking list is empty");
            return;
        }
        for(Booking booking : bookingList){
            Optional<Show> show = Optional.of(showRepo.findById(booking.getShowId())
                    .orElseThrow(() -> new ShowNotFoundException("show not found")));
            SeatManager seatManager=show.get().getSeatManagerId();
            Seat seat =seatManager.getSeats().get(booking.getSeatNumber());
            seat .setSeatStatus(SeatStatus.OPEN);
            showRepo.save(show.get());
        }

    }

    @Override
    public Receipt generateReceipt(String bookingId) {
      Booking booking =  bookingRepo.findById(bookingId)
              .orElseThrow(()-> new BookingNotFound("BOOKING NOT FOUND"));

      Show show=showRepo.findById(booking.getShowId())
              .orElseThrow(()-> new ShowNotFoundException("SHOW NOT FOUND"));

     Movies movies= movieRepo.findMovieByMovieId(show.getMoviesId())
              .orElseThrow(()-> new MovieNotFoundException("MOVIE NOT FOUND"));
     return Mapper.createReceipt(booking,show,movies);
    }


    private void verifySeatStatus(Seat seat){
        if(seat.getSeatStatus() == SeatStatus.LOCKED){
            throw  new SeatDetailException("Seat is locked");
        }else if( seat.getSeatStatus()==SeatStatus.UNDERMAINTENANCE){
            throw  new SeatDetailException("Seat is undermaintenance");
        }
    }
}
