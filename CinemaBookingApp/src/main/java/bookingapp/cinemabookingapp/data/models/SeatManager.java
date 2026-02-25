package bookingapp.cinemabookingapp.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Document
public class SeatManager {
    private String id;
    private Map<Integer,Seat> seats;

    public SeatManager(int numberOfSeats,int numberOfRow) {
        this.seats = new HashMap<>();
        createSeat(numberOfSeats,numberOfRow);
    }
    public void createSeat(int numberOfSeats,int numberOfRow) {
        for (int i = 0; i < numberOfRow; i++) {
            for(int j = 0; j < numberOfSeats; j++){
                Seat seat = new Seat(i,numberOfRow+numberOfSeats);
                seats.put(seat.getSeatNumber(),seat);
            }
        }
    }
}
