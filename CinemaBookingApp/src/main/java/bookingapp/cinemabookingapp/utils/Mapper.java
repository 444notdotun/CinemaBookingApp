package bookingapp.cinemabookingapp.utils;

import bookingapp.cinemabookingapp.data.models.*;
import bookingapp.cinemabookingapp.data.repository.ShowRepository;
import bookingapp.cinemabookingapp.dtos.request.*;
import bookingapp.cinemabookingapp.dtos.response.AddShowToTheaterResponse;
import bookingapp.cinemabookingapp.dtos.response.CreateAdminResponse;
import bookingapp.cinemabookingapp.dtos.response.CreateShowResponse;
import bookingapp.cinemabookingapp.dtos.response.CreateTheaterResponse;
import org.springframework.beans.factory.annotation.Autowired;

public class Mapper {
    public static Admin mapAdminRequestToTheaterAdmin(CreateAdminRequest createAdminRequest) {
        return new TheaterAdmin(createAdminRequest.getCity()+"Theater Admin",createAdminRequest.getPassword(), Role.THEATER_ADMIN);
    }

    public static CreateAdminResponse MapAdminToResponse(Admin admin) {
        CreateAdminResponse createAdminResponse = new CreateAdminResponse();
        createAdminResponse.setMessage(admin.getId()+" created successfully");
        return createAdminResponse;
    }

    public static Theater MapTheaterRequestToTheater(CreateTheaterRequest createTheater) {
        return new Theater(createTheater.getCity(),createTheater.getName());}

    public static CreateTheaterResponse SetCreateTheaterResponse(Theater theater) {
        CreateTheaterResponse createTheaterResponse = new CreateTheaterResponse();
        createTheaterResponse.setMessage(theater.getId()+" created successfully");
        return createTheaterResponse;
    }


    public static AddShowToTheaterResponse MapAddShowResponse(AddShowTheaterRequest theater) {
        AddShowToTheaterResponse addShowToTheaterResponse = new AddShowToTheaterResponse();
        addShowToTheaterResponse.setMessage(theater.getShowId()+" added successfully");
        return addShowToTheaterResponse;
    }

    public static Show mapDtosToShow(CreateShowManagerRequest createShowManagerRequest) {
        SeatManager seatManager= new SeatManager(createShowManagerRequest.getNoOfSeats(),createShowManagerRequest.getRowsOfSeats());
        return new Show("", createShowManagerRequest.getMoviesId(), createShowManagerRequest.getDuration(), createShowManagerRequest.getStartTime(),createShowManagerRequest.getPrice(),seatManager);

    }

    public static CreateShowResponse mapShowToCreateShowResponse(Show show) {
        CreateShowResponse createShowResponse = new CreateShowResponse();
        createShowResponse.setMessage("Show created successfully");
        return  createShowResponse;
    }

    public static Movies mapMovieRequestToMovie(CreateMovieRequest createMovieRequest) {
        return new Movies("",createMovieRequest.getDescription(), createMovieRequest.getTitle(), createMovieRequest.getDirector(), createMovieRequest.getDuration(), createMovieRequest.getRating(), createMovieRequest.getPosterUrl());
    }
}
