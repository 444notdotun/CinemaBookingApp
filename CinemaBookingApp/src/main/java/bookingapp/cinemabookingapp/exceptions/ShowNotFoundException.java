package bookingapp.cinemabookingapp.exceptions;

public class ShowNotFoundException extends CinemaBookingApp {
    public ShowNotFoundException(String showNotFound) {
        super(showNotFound);
    }
}
