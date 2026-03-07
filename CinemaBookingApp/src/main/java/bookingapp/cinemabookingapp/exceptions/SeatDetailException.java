package bookingapp.cinemabookingapp.exceptions;

public class SeatDetailException extends CinemaBookingApp {
    public SeatDetailException(String seatIsLocked) {
        super(seatIsLocked);
    }
}
