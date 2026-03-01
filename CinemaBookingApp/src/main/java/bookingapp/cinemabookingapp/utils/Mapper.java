package bookingapp.cinemabookingapp.utils;

import bookingapp.cinemabookingapp.data.models.*;
import bookingapp.cinemabookingapp.dtos.request.*;
import bookingapp.cinemabookingapp.dtos.response.*;

import java.time.LocalTime;

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
        createTheaterResponse.setMessage(theater.getName()+" theater created successfully");
        createTheaterResponse.setId(theater.getId());
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
        createShowResponse.setId(show.getId());
        return  createShowResponse;
    }

    public static Movies mapMovieRequestToMovie(CreateMovieRequest createMovieRequest) {
        return new Movies("",createMovieRequest.getDescription(), createMovieRequest.getTitle(), createMovieRequest.getDirector(), createMovieRequest.getDuration(), createMovieRequest.getRating(), createMovieRequest.getPosterUrl());
    }

    public static Booking MapRequestToBooking(BookShowRequest bookShowRequest) {
        Booking booking = new Booking();
        booking.setSeatNumber(bookShowRequest.getSeatNumber());
        booking.setUserName(bookShowRequest.getUserName());
        booking.setShowId(bookShowRequest.getShowId());
        return booking;
    }

    public static BookShowResponse MapBookingToResponse(Booking booking) {
        BookShowResponse bookShowResponse = new BookShowResponse();
        bookShowResponse.setBookingId(booking.getId());
        bookShowResponse.setMessage("hi "+booking.getUserName() +" you've successfully booked successfully yourself a seat, head to the payment platform to finally secure it");
        return bookShowResponse;
    }
}
