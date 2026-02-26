package bookingapp.cinemabookingapp.dtos.request;

import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import org.hibernate.validator.constraints.URL;

@Data
public class CreateMovieRequest {
    @NotBlank(message = "title cannot be blank")
    @Size(min = 2, max = 100)
    private String title;

    @NotBlank(message = "director cannot be blank")
    @Size(min = 2, max = 100)
    private String director;

    @NotBlank(message = "duration cannot be blank")
    private String duration;

    @NotBlank(message = "description cannot be blank")
    @Size(min = 10, max = 500)
    private String description;

    @DecimalMin(value = "0.0", message = "rating cannot be negative")
    @DecimalMax(value = "10.0", message = "rating cannot exceed 10")
    private double rating;

    @NotBlank(message = "poster URL cannot be blank")
    @URL(message = "must be a valid URL")
    private String posterUrl;
}
