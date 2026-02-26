package bookingapp.cinemabookingapp.dtos.response;

import bookingapp.cinemabookingapp.data.models.Theater;
import lombok.Data;

import java.util.List;
@Data
public class GetTheaterResponse {
    private List<Theater> theaters;
}
