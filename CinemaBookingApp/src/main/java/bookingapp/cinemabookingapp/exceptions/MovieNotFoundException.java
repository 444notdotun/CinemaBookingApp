package bookingapp.cinemabookingapp.exceptions;

public class MovieNotFoundException extends CinemaBookingApp{
    public MovieNotFoundException(String movieNotFound) {
        super(movieNotFound);
    }
}
