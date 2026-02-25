package bookingapp.cinemabookingapp.dtos.request;

import bookingapp.cinemabookingapp.data.models.Movies;
import bookingapp.cinemabookingapp.data.models.SeatManager;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalTime;


@Data
public class CreateShowManagerRequest {
    private String moviesId;
    private String duration;
    private LocalTime startTime;
    private BigDecimal price;
    private String seatManagerId;
    private int noOfSeats;
    private int rowsOfSeats;
}
