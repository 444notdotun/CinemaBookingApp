package bookingapp.cinemabookingapp.dtos.request;

import lombok.Data;

@Data
public class CreateTheaterRequest {
    private String city;
    private String name;
}
