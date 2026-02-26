package bookingapp.cinemabookingapp.service.interfaces;

import bookingapp.cinemabookingapp.dtos.response.ShowDescriptionResponse;

public interface ShowService {

    ShowDescriptionResponse ShowDescription(String id);

}
