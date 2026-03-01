package bookingapp.cinemabookingapp.service.Implementation;

import bookingapp.cinemabookingapp.data.models.Seat;
import bookingapp.cinemabookingapp.service.interfaces.SeatService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.junit.jupiter.api.Assertions.*;

class SeatServiceImplTest {
    @Autowired
    MongoTemplate mongoTemplate;
    @Autowired
    SeatService seatService;


    @BeforeEach
    void setUp() {

    }

    @Test
    void testThatSeatCanChangeState() {
        Seat seat = new Seat();
        seatService.lockSeat();

    }


}