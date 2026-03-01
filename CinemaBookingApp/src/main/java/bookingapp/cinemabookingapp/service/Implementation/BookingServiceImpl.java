package bookingapp.cinemabookingapp.service.Implementation;

import bookingapp.cinemabookingapp.data.models.*;
import bookingapp.cinemabookingapp.data.repository.BookingRepository;
import bookingapp.cinemabookingapp.data.repository.ShowRepository;
import bookingapp.cinemabookingapp.dtos.request.BookShowRequest;
import bookingapp.cinemabookingapp.dtos.response.BookShowResponse;
import bookingapp.cinemabookingapp.service.interfaces.BookingService;
import bookingapp.cinemabookingapp.utils.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    @Override
    @Transactional
    public BookShowResponse bookShow(BookShowRequest bookShowRequest) {
        Show show = showRepo.findById(bookShowRequest.getShowId())
                .orElseThrow(()->new RuntimeException("Show not found"));
        SeatManager seatManager =show.getSeatManagerId();
        Seat seat = seatManager.getSeats().get(bookShowRequest.getSeatNumber());
        verifySeatStatus(seat);
        Booking booking = Mapper.MapRequestToBooking(bookShowRequest);
        seat.setSeatStatus(SeatStatus.LOCKED);
        show.setSeatManagerId(seatManager);
        showRepo.save(show);
        log.info("booking request received");
        booking.setId(idGeneratorServices.generateId("booking"));
        bookingRepo.save(booking);
        return Mapper.MapBookingToResponse(booking);
    }

    @Transactional
    @Override
    @Scheduled(fixedRate = 3000)
    public void unBook() {
        List<Booking> bookingList = new ArrayList<>();
        bookingList=bookingRepo.findByPaymentStatusInAndExpirationTimeBefore(PaymentStatus.PAYMENT_PENDING, LocalDateTime.now());
        if(bookingList.isEmpty()){
            log.info("booking list is empty");
        }
       System.out.println(bookingRepo.findAll());
        for(Booking booking : bookingList){
            Optional<Show> show = Optional.of(showRepo.findById(booking.getShowId())
                    .orElseThrow(() -> new RuntimeException("show not found")));
            SeatManager seatManager=show.get().getSeatManagerId();
            Seat seat =seatManager.getSeats().get(booking.getSeatNumber());
            seat .setSeatStatus(SeatStatus.OPEN);
            System.out.println(seat);
            System.out.println(show);
            System.out.println(seatManager);
            showRepo.save(show.get());
        }

    }


    private void verifySeatStatus(Seat seat){
        if(seat.getSeatStatus() == SeatStatus.LOCKED){
            throw  new RuntimeException("Seat is locked");
        }else if( seat.getSeatStatus()==SeatStatus.UNDERMAINTENANCE){
            throw  new RuntimeException("Seat is undermaintenance");
        }
    }
}
