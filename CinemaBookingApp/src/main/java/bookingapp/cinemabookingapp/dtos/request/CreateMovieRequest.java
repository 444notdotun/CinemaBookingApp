package bookingapp.cinemabookingapp.dtos.request;

import lombok.Data;

@Data
public class CreateMovieRequest {
    private String  title;
    private String director;
    private String duration;
    private String description;
    private double rating;
    private String posterUrl;
}
