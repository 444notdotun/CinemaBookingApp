package bookingapp.cinemabookingapp.dtos.request;

import lombok.Data;

@Data
public class CreateAdminRequest {
    private String city;
    private String password;
}
