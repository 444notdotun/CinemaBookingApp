package bookingapp.cinemabookingapp.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Seat {
    private String id;
    private int row;
    private int seatNumber;
    private SeatStatus seatStatus;

    public Seat(int id,int seatNumber, int row ) {
        this.seatNumber = seatNumber;
        this.row = row;
        this.id =("seat"+id);
        this.seatStatus = SeatStatus.OPEN;
    }
}
