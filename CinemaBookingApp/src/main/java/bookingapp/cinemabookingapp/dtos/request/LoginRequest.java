package bookingapp.cinemabookingapp.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class LoginRequest {
    @NotBlank(message = "can not be blank")
    @NotNull(message = "not be null")
    private String id;
    @NotBlank(message = "can not be blank")
    @NotNull(message = "not be null")
    private String password;
}
