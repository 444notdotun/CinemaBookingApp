package bookingapp.cinemabookingapp.service.interfaces;

import bookingapp.cinemabookingapp.dtos.request.SendEmailRequest;

public interface EmailServices {

    void sendTicketEmail(SendEmailRequest sendEmailRequest);
}
