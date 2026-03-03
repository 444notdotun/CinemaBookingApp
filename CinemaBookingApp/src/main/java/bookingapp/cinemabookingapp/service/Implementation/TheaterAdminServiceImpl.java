package bookingapp.cinemabookingapp.service.Implementation;

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
import bookingapp.cinemabookingapp.dtos.response.CreateMovieResponse;
import bookingapp.cinemabookingapp.dtos.response.CreateShowResponse;
import bookingapp.cinemabookingapp.dtos.response.CreateTheaterResponse;
import bookingapp.cinemabookingapp.service.interfaces.TheaterAdminService;
import bookingapp.cinemabookingapp.utils.Mapper;
import lombok.extern.slf4j.Slf4j;
import org.jspecify.annotations.NullMarked;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

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

    @Autowired
    IdGeneratorServices idGeneratorServices;




    @Override
    public AddShowToTheaterResponse addShowToTheater(AddShowTheaterRequest addShowTheaterRequest) {
        Optional<Theater> theater = theaterRepository.findById(addShowTheaterRequest.getTheaterId());
      if(theater.isEmpty()){
          throw new RuntimeException(addShowTheaterRequest.getTheaterId()+" not found");
      }
      theater.get().getShowsId().add(addShowTheaterRequest.getShowId());
      theaterRepository.save(theater.get());
      return Mapper.MapAddShowResponse(addShowTheaterRequest);
    }

    @Override
    public CreateTheaterResponse createTheater(CreateTheaterRequest createTheater) {
       Theater theater= Mapper.MapTheaterRequestToTheater(createTheater);
       theater.setId(idGeneratorServices.generateId("theater"));
        theaterRepository.save(theater);
        log.info(" created successfully");
        return Mapper.SetCreateTheaterResponse(theater);
    }

    @Override
    public CreateShowResponse createShow(CreateShowManagerRequest createShowManagerRequest) {
      Optional<Movies> movies = Optional.ofNullable(movieRepo.findMovieByMovieId(createShowManagerRequest.getMoviesId())
              .orElseThrow(() -> new RuntimeException("Movie not found")));
        Show show =Mapper.mapDtosToShow(createShowManagerRequest);
        show.setId(idGeneratorServices.generateId("show"));
        show.setMoviesId(movies.get().getMovieId());
        showRepo.save(show);
        log.info("show created successfully");
        return Mapper.mapShowToCreateShowResponse(show);
    }

    @Override
    public CreateMovieResponse createMovie(CreateMovieRequest createMovieRequest) {
       Movies movies =  Mapper.mapMovieRequestToMovie(createMovieRequest);
       log.info("movie created successfully");
       movies.setMovieId(idGeneratorServices.generateId("movie"));
      movieRepo.save(movies);
        CreateMovieResponse createMovieResponse = new CreateMovieResponse();
        createMovieResponse.setMessage("movie created successfully");
        createMovieResponse.setId(movies.getMovieId());
      return createMovieResponse;
    }




}
