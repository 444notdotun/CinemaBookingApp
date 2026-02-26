package bookingapp.cinemabookingapp.dtos.response;

import bookingapp.cinemabookingapp.data.models.Show;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class GetShows {

    private List<Show> shows;

    public  GetShows() {
        this.shows = new ArrayList<>();
    }
}
