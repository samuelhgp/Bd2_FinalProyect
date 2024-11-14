package backend.bd_proyect.Service;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.http.ResponseEntity;

import backend.bd_proyect.DTOs.LoginResponse;
import backend.bd_proyect.Model.Users.Credentials;
import backend.bd_proyect.Model.Users.UsersModel;

public interface IUsersService {
    //aggregations
    List<UsersModel>BooksByUser(String userName);

    List<UsersModel>NonAcceptedExchanges(String Username);

    // Métodos de gestión de perfil
    UsersModel createUserProfile(UsersModel user);
    UsersModel updateUserProfile(String id, UsersModel user);
    void deleteUserProfile(String id);
    void recoverCredentials(String email);

    // Métodos de autenticación y registro
    ResponseEntity<LoginResponse> login(Credentials credentials); // Añadir firma de método login
    UsersModel register(UsersModel user);  // Añadir firma de método register
    void logout();                         // Añadir firma de método logout

    void followUser(ObjectId userId, ObjectId followUserId);
    void unfollowUser(ObjectId userId, ObjectId unfollowUserId);
    List<ObjectId> viewFollowers(ObjectId userId);
    List<ObjectId> viewFollowing(ObjectId userId);
}
