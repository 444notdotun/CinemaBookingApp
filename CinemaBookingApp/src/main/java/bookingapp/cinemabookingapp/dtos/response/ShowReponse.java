package bookingapp.cinemabookingapp.dtos.response;

import bookingapp.cinemabookingapp.data.models.SeatManager;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalTime;
@Data
public class ShowReponse {
    private String moviesId;
    private String duration;
    private LocalTime startTime;
    private BigDecimal price;
    private SeatManager seatManagerId;
}
