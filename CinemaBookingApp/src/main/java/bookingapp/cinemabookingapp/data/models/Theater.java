package bookingapp.cinemabookingapp.data.models;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.List;
@Document("Theater")
@Data
public class Theater {
    private String id;
    private String city;
    private List<String> showsId;
    private String name;

    public Theater(String city,String name) {
        this.city = city;
        this.name = name;
        this.showsId = new ArrayList<>();
    }
}
