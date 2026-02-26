package bookingapp.cinemabookingapp.data.models;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Data
@Document
@NoArgsConstructor
public class SeatManager {
    private String id;
    private Map<String,Seat> seats;

    public SeatManager(int numberOfSeats,int numberOfRow) {
        this.seats = new HashMap<>();
        createSeat(numberOfSeats,numberOfRow);
    }
    public void createSeat(int numberOfSeats,int numberOfRow) {
        int id = 30;
        for (int i = 0; i < numberOfRow; i++) {
            for(int j = 0; j < numberOfSeats; j++){
                Seat seat = new Seat(id,i,j);
                seats.put(seat.getId(), seat);
                id++;
            }
        }
    }
}
