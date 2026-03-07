package bookingapp.cinemabookingapp.service.interfaces;

import bookingapp.cinemabookingapp.dtos.response.ShowDescriptionResponse;
import bookingapp.cinemabookingapp.dtos.response.ShowReponse;

public interface ShowService {

    ShowDescriptionResponse ShowDescription(String id);

    ShowReponse getShow(String showId);
}
