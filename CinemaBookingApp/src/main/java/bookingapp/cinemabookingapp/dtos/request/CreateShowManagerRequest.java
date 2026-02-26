package bookingapp.cinemabookingapp.dtos.request;

import bookingapp.cinemabookingapp.data.models.Movies;
import bookingapp.cinemabookingapp.data.models.SeatManager;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;
import java.math.BigDecimal;
import java.time.LocalTime;


@Data
public class CreateShowManagerRequest {
    @NotBlank(message = "cannot br blank")
    private String moviesId;
    @NotBlank(message = "cannot br blank")
    private String duration;
    @NotNull(message = "cannot be blank")
    private LocalTime startTime;
    @NotNull(message = "cannot be blank")
    private BigDecimal price;
    @NotBlank(message = "cannot br blank")
    private String seatManagerId;
    @Positive(message = "must be greater than 0")
    private int noOfSeats;
    @Positive(message = "must be greater than 0")
    private int rowsOfSeats;
}
