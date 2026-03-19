package bookingapp.cinemabookingapp.data.models;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "admins")
public class SuperAdmin extends Admin{

    public SuperAdmin(String id, String Password, Role role) {
        super(id, Password, role);
    }
}
