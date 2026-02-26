package bookingapp.cinemabookingapp.service.Implementation;

import bookingapp.cinemabookingapp.data.models.Movies;
import bookingapp.cinemabookingapp.data.models.Show;
import bookingapp.cinemabookingapp.data.repository.MovieRepo;
import bookingapp.cinemabookingapp.data.repository.ShowRepository;
import bookingapp.cinemabookingapp.dtos.response.ShowDescriptionResponse;
import bookingapp.cinemabookingapp.service.interfaces.ShowService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
public class ShowServiceImpl implements ShowService {
    @Autowired
    ShowRepository  showRepository;
    @Autowired
    MovieRepo  movieRepo;
    @Override
    public ShowDescriptionResponse ShowDescription(String id) {
        Optional<Show> show = showRepository.findById(id);
        if (show.isEmpty()){
            throw  new RuntimeException("Show not found");
        }
        String MovieID = show.get().getMoviesId();
        Optional<Movies> movies = movieRepo.findMovieByMovieId(MovieID);
        ShowDescriptionResponse showDescriptionResponse = new ShowDescriptionResponse();
        showDescriptionResponse.setDescription(movies.get().getDescription());
        return showDescriptionResponse;
    }
}
