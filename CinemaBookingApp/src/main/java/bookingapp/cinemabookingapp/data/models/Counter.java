package bookingapp.cinemabookingapp.data.models;


import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.mongodb.core.mapping.Document;

@Document
@Data
public class Counter {
    private String id;
    private int seq;


}
