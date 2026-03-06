package bookingapp.cinemabookingapp.data.models;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.math.BigDecimal;
import java.time.LocalTime;
@AllArgsConstructor
@Data
@Document
public class Show {
    private String id;
    private String moviesId;
    private String duration;
    private LocalTime startTime;
    private BigDecimal price;
    private SeatManager seatManagerId;
}