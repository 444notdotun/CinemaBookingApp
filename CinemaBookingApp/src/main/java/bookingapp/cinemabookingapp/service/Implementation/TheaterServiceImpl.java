package bookingapp.cinemabookingapp.service.Implementation;

import bookingapp.cinemabookingapp.data.models.Show;
import bookingapp.cinemabookingapp.data.models.Theater;
import bookingapp.cinemabookingapp.data.repository.ShowRepository;
import bookingapp.cinemabookingapp.data.repository.TheaterRepository;
import bookingapp.cinemabookingapp.dtos.response.GetShows;
import bookingapp.cinemabookingapp.dtos.response.GetTheaterResponse;
import bookingapp.cinemabookingapp.service.interfaces.TheaterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class TheaterServiceImpl implements TheaterService {
    @Autowired
    TheaterRepository theaterRepository;
    @Autowired
    ShowRepository showRepository;

    @Override
    public GetTheaterResponse GetAllTheater() {
        GetTheaterResponse response = new GetTheaterResponse();
       response.setTheaters(theaterRepository.findAll());
        return  response;
    }

    @Override
    public GetShows getAllShows(String id) {
        List<Show> shows = new ArrayList<>();
        GetShows response = new GetShows();
        Theater theater =  theaterRepository.findById(id).get();
        for(String show: theater.getShowsId()){
            shows.add(showRepository.findById(show).get());
        }
        response.setShows(shows);
        return  response;
    }


}
