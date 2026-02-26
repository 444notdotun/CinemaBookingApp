package bookingapp.cinemabookingapp.service.interfaces;

import bookingapp.cinemabookingapp.dtos.response.GetShows;
import bookingapp.cinemabookingapp.dtos.response.GetTheaterResponse;
import org.jspecify.annotations.Nullable;

public interface TheaterService {
    GetTheaterResponse GetAllTheater();
    GetShows getAllShows(String id);
}
