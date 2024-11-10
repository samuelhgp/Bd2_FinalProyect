package backend.bd_proyect.Model.Users;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "Users")

public class UsersModel {
    @Id
    private ObjectId idUser;
    private String full_name;
    private Address address; 
    private String profilePicture;
    private String description;
    private Credentials credentials;
    private List<ObjectId> followers;   
    private List<ObjectId> following;  

    public String getName() {
        return full_name;
    }
}

