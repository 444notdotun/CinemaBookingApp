package bookingapp.cinemabookingapp.data.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@AllArgsConstructor
@Document
public class Movies {
    private String movieId;
    private String description;
    private String title;
    private String director;
    private String duration;
    private double rating;
    private  String posterUrl;
}
