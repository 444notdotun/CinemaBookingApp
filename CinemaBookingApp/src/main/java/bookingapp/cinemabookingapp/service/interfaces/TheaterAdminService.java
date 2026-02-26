package bookingapp.cinemabookingapp.service.interfaces;

import bookingapp.cinemabookingapp.dtos.request.AddShowTheaterRequest;
import bookingapp.cinemabookingapp.dtos.request.CreateMovieRequest;
import bookingapp.cinemabookingapp.dtos.request.CreateShowManagerRequest;
import bookingapp.cinemabookingapp.dtos.request.CreateTheaterRequest;
import bookingapp.cinemabookingapp.dtos.response.AddShowToTheaterResponse;
import bookingapp.cinemabookingapp.dtos.response.CreateShowResponse;
import bookingapp.cinemabookingapp.dtos.response.CreateTheaterResponse;
import bookingapp.cinemabookingapp.service.Implementation.CreateMovieResponse;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface TheaterAdminService{

    AddShowToTheaterResponse addShowToTheater(AddShowTheaterRequest  addShowTheaterRequest);
    CreateTheaterResponse createTheater( CreateTheaterRequest createTheater);

     CreateShowResponse createShow(CreateShowManagerRequest createShowManagerRequest);
    CreateMovieResponse createMovie(CreateMovieRequest createMovieRequest);
}
