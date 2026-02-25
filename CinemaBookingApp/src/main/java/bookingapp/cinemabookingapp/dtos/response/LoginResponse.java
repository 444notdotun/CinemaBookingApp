package bookingapp.cinemabookingapp.dtos.response;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class LoginResponse {
    private String id;
    private String accessToken;
    private String refreshToken;

}
