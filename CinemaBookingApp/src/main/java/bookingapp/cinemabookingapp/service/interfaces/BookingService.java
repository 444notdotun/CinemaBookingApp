package bookingapp.cinemabookingapp.service.interfaces;

import bookingapp.cinemabookingapp.data.models.Booking;
import bookingapp.cinemabookingapp.data.models.Movies;
import bookingapp.cinemabookingapp.data.models.Show;
import bookingapp.cinemabookingapp.dtos.request.BookShowRequest;
import bookingapp.cinemabookingapp.dtos.response.BookShowResponse;
import bookingapp.cinemabookingapp.dtos.response.Receipt;

public interface BookingService {
    BookShowResponse bookShow(BookShowRequest bookShowRequest);

    void unBook();

    Receipt generateReceipt(String bookingId);
}
