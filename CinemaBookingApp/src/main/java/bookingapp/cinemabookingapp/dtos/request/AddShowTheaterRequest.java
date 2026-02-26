package bookingapp.cinemabookingapp.dtos.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class AddShowTheaterRequest {
    @NotBlank(message = "can not be blank")
    private String showId;
    @NotBlank(message = "can not be blank")
    private String theaterId;
}
