package bookingapp.cinemabookingapp.service.interfaces;

import bookingapp.cinemabookingapp.dtos.request.BookShowRequest;
import bookingapp.cinemabookingapp.dtos.response.BookShowResponse;

public interface BookingService {
    BookShowResponse bookShow(BookShowRequest bookShowRequest);

    void unBook();
}
