package bookingapp.cinemabookingapp.dtos.request;

import lombok.Data;

@Data
public class BookShowRequest {
    private String showId;
    private String userName;
    private String seatNumber;
}
