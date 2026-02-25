package bookingapp.cinemabookingapp.data.models;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

@Document
public class TheaterAdmin extends Admin {


    public TheaterAdmin(String id, String Password, Role role) {
        super(id, Password, role);
    }


}
