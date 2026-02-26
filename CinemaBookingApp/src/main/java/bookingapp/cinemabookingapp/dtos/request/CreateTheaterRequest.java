package bookingapp.cinemabookingapp.dtos.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CreateTheaterRequest {
    @NotBlank(message = "can not be blank")
    @NotNull(message = "not be null")
    @Size(min = 3)
    private String city;
    @NotBlank(message = "can not be blank")
    @NotNull(message = "not be null")
    @Size(min = 3)
    private String name;
}
