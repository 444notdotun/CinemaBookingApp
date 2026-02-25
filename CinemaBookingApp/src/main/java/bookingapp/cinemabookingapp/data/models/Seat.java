package bookingapp.cinemabookingapp.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class Seat {

    private int row;
    private int seatNumber;
    private SeatStatus seatStatus;

    public Seat(int seatNumber, int row ) {
        this.seatNumber = seatNumber;
        this.row = row;
        this.seatStatus = SeatStatus.OPEN;
    }
}
