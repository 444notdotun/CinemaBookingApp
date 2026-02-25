package bookingapp.cinemabookingapp.dtos.request;

import lombok.Data;

@Data
public class LoginRequest {
    private String id;
    private String password;
}
