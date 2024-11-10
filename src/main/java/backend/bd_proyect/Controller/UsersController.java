package backend.bd_proyect.Controller;

import backend.bd_proyect.DTOs.LoginResponse;
import backend.bd_proyect.Model.Users.Credentials;
import backend.bd_proyect.Model.Users.UsersModel;
import backend.bd_proyect.Service.IUsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.bson.types.ObjectId;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UsersController {

    @Autowired
    private IUsersService usersService;

    @PostMapping("/create")
    public ResponseEntity<String> createUserProfile(@RequestBody UsersModel user) {
        usersService.createUserProfile(user);
        String message = "El perfil del usuario ha sido creado exitosamente.";
        return new ResponseEntity<>(message, HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<UsersModel> updateUserProfile(@PathVariable("id") String id, @RequestBody UsersModel user) {
        UsersModel updatedUser = usersService.updateUserProfile(id, user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteUserProfile(@PathVariable("id") String id) {
        usersService.deleteUserProfile(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    // Endpoint para recuperación de credenciales
    @PostMapping("/recover")
    public ResponseEntity<String> recoverCredentials(@RequestParam String email) {
        try {
            usersService.recoverCredentials(email);
            return ResponseEntity.status(HttpStatus.OK).body("Se ha enviado un correo de recuperación de credenciales.");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
    }

    // Autenticación y credenciales
    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody Credentials credentials) {
        return usersService.login(credentials);
    }

    @PostMapping("/register")
    public ResponseEntity<UsersModel> register(@RequestBody UsersModel user) {
        UsersModel registeredUser = usersService.register(user);
        return new ResponseEntity<>(registeredUser, HttpStatus.CREATED);
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout() {
        usersService.logout();
        return new ResponseEntity<>("Logged out successfully", HttpStatus.OK);
    }

    // Métodos para conexiones sociales
    @PostMapping("/follow")
    public ResponseEntity<String> followUser(@RequestParam String userId, @RequestParam String followUserId) {
        usersService.followUser(new ObjectId(userId), new ObjectId(followUserId));
        return ResponseEntity.ok("User followed successfully");
    }

    @PostMapping("/unfollow")
    public ResponseEntity<String> unfollowUser(@RequestParam String userId, @RequestParam String unfollowUserId) {
        usersService.unfollowUser(new ObjectId(userId), new ObjectId(unfollowUserId));
        return new ResponseEntity<>("User unfollowed successfully", HttpStatus.OK);
    }

    @GetMapping("/followers/{userId}")
    public ResponseEntity<List<ObjectId>> viewFollowers(@PathVariable String userId) {
        List<ObjectId> followers = usersService.viewFollowers(new ObjectId(userId));
        return new ResponseEntity<>(followers, HttpStatus.OK);
    }

    @GetMapping("/following/{userId}")
    public ResponseEntity<List<ObjectId>> viewFollowing(@PathVariable String userId) {
        List<ObjectId> following = usersService.viewFollowing(new ObjectId(userId));
        return new ResponseEntity<>(following, HttpStatus.OK);
    }
}
