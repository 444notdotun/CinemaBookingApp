package bookingapp.cinemabookingapp.dtos.request;

import lombok.Data;

@Data
public class AddShowTheaterRequest {
    private String ShowId;
    private String theaterId;
}
