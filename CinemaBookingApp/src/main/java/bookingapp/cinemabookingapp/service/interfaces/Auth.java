package bookingapp.cinemabookingapp.service.interfaces;

import bookingapp.cinemabookingapp.dtos.request.LoginRequest;
import bookingapp.cinemabookingapp.dtos.response.LoginResponse;

public interface Auth {
     LoginResponse login (LoginRequest loginRequest);
}
