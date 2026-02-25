package bookingapp.cinemabookingapp.service.Implementation;

import bookingapp.cinemabookingapp.data.models.Admin;
import bookingapp.cinemabookingapp.data.models.Movies;
import bookingapp.cinemabookingapp.data.models.Show;
import bookingapp.cinemabookingapp.data.models.Theater;
import bookingapp.cinemabookingapp.data.repository.AdminRepo;
import bookingapp.cinemabookingapp.data.repository.MovieRepo;
import bookingapp.cinemabookingapp.data.repository.ShowRepository;
import bookingapp.cinemabookingapp.data.repository.TheaterRepository;
import bookingapp.cinemabookingapp.dtos.request.AddShowTheaterRequest;
import bookingapp.cinemabookingapp.dtos.request.CreateMovieRequest;
import bookingapp.cinemabookingapp.dtos.request.CreateShowManagerRequest;
import bookingapp.cinemabookingapp.dtos.request.CreateTheaterRequest;
import bookingapp.cinemabookingapp.dtos.response.AddShowToTheaterResponse;
import bookingapp.cinemabookingapp.dtos.response.CreateShowResponse;
import bookingapp.cinemabookingapp.dtos.response.CreateTheaterResponse;
import bookingapp.cinemabookingapp.service.interfaces.TheaterAdminService;
import bookingapp.cinemabookingapp.utils.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Slf4j
@NullMarked
public class TheaterAdminServiceImpl implements TheaterAdminService {
    @Autowired
    TheaterRepository theaterRepository;
    @Autowired
    AdminRepo adminRepo;
    @Autowired
    ShowRepository showRepo;
    @Autowired
    MovieRepo movieRepo;

    private static int counter= 10;




    @Override
    public AddShowToTheaterResponse addShowToTheater(AddShowTheaterRequest addShowTheaterRequest) {
        Optional<Theater> theater = theaterRepository.findById(addShowTheaterRequest.getTheaterId());
      if(theater.isEmpty()){
          throw new RuntimeException(addShowTheaterRequest.getTheaterId()+" not found");
      }
      theater.get().setShowsId(List.of(addShowTheaterRequest.getShowId()));
      theaterRepository.save(theater.get());
      return Mapper.MapAddShowResponse(addShowTheaterRequest);
    }

    @Override
    public CreateTheaterResponse createTheater(CreateTheaterRequest createTheater) {
       Theater theater= Mapper.MapTheaterRequestToTheater(createTheater);
       theater.setId("Theater"+counter++);
        theaterRepository.save(theater);
        log.info("theater created successfully");
        return Mapper.SetCreateTheaterResponse(theater);
    }

    @Override
    public CreateShowResponse createShow(CreateShowManagerRequest createShowManagerRequest) {
       if (movieRepo.findMovieByMovieId(createShowManagerRequest.getMoviesId()).isEmpty()){
           log.info("movie not found");
           throw new RuntimeException(" movie not found, create the movie");
       }
        Show show =Mapper.mapDtosToShow(createShowManagerRequest);
        show.setId("show"+counter++);
        log.info("show created successfully");
        return Mapper.mapShowToCreateShowResponse(show);
    }

    @Override
    public String createMovie(CreateMovieRequest createMovieRequest) {
       Movies movies =  Mapper.mapMovieRequestToMovie(createMovieRequest);
       log.info("movie created successfully");
       movies.setMovieId("Movie"+counter++);
      movieRepo.save(movies);
      return   movies.getTitle()+" created successfully";
    }




}
