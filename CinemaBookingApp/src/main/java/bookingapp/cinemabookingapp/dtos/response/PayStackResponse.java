package bookingapp.cinemabookingapp.dtos.response;

import lombok.Data;

@Data
public class PayStackResponse{
    private String access_code;
    private String authorization_url;
    private String reference;
}
