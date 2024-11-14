package backend.bd_proyect.Service;

import backend.bd_proyect.DTOs.LoginResponse;
import backend.bd_proyect.Model.Users.Credentials;
import backend.bd_proyect.Model.Users.UsersModel;
import backend.bd_proyect.Repository.UsersRepository;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.crypto.SecretKey;

@Service
public class UsersServiceImpl implements IUsersService {

    @Autowired
    private EmailService emailService;

    @Autowired
    private UsersRepository usersRepository;

    private static final String SECRET_KEY = "YourSuperSecretKeyThatIsAtLeast32CharsLong";

    private static final SecretKey KEY = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    // Crear un perfil de usuario
    @Override
    public UsersModel createUserProfile(UsersModel user) {
        Optional<UsersModel> existingUser = usersRepository.findByCredentials_Email(user.getCredentials().getEmail());

        if (existingUser.isPresent()) {
            throw new RuntimeException("El correo ya está registrado.");
        }

        return usersRepository.save(user);
    }

    // Actualizar un perfil de usuario
    @Override
    public UsersModel updateUserProfile(String id, UsersModel user) {
        UsersModel existingUser = usersRepository.findById(new ObjectId(id))
                .orElseThrow(() -> new RuntimeException("User not found"));
        existingUser.setFull_name(user.getFull_name());
        existingUser.setAddress(user.getAddress());
        existingUser.setDescription(user.getDescription());
        existingUser.setProfilePicture(user.getProfilePicture());
        return usersRepository.save(existingUser);
    }

    // Eliminar un perfil de usuario
    @Override
    public void deleteUserProfile(String id) {
        usersRepository.deleteById(new ObjectId(id));
    }

    // Recuperación de credenciales mediante correo electrónico
    @Override
    public void recoverCredentials(String email) {
        // Usar el método findByCredentialsEmail del repositorio y manejar el Optional
        Optional<UsersModel> optionalUser = usersRepository.findByCredentials_Email(email);

        if (optionalUser.isPresent()) {
            UsersModel user = optionalUser.get();
            Credentials credentials = user.getCredentials();

            // Enviar correo de recuperación
            String subject = "Recuperación de Credenciales de Acceso";
            String message = "Hola " + user.getName() + ",\n\n" +
                    "Tus credenciales de acceso son:\n" +
                    "Usuario: " + credentials.getUsername() + "\n" +
                    "Contraseña: " + credentials.getPassword() + "\n\n" +
                    "Por favor, cambia tu contraseña después de iniciar sesión.\n\n" +
                    "Saludos,\nEquipo de Soporte";

            emailService.sendEmail(email, subject, message);
        } else {
            throw new RuntimeException("No se encontró ningún usuario con el correo especificado.");
        }
    }

    // Registro de usuario
    @Override
    public UsersModel register(UsersModel user) {
        // Comprueba si el correo ya está registrado utilizando Optional
        Optional<UsersModel> existingUser = usersRepository.findByCredentials_Email(user.getCredentials().getEmail());

        if (existingUser.isPresent()) {
            throw new RuntimeException("Email is already registered.");
        }

        return usersRepository.save(user);
    }

    // Inicio de sesión
    @Override
    public ResponseEntity<LoginResponse> login(Credentials credentials) {
        Optional<UsersModel> optionalUser = usersRepository.findByCredentials_Email(credentials.getEmail());

        // Verificar si el usuario existe y que las credenciales coincidan
        if (optionalUser.isPresent()) {
            UsersModel user = optionalUser.get();
            if (user.getCredentials().getPassword().equals(credentials.getPassword())) {
                // Generar el token JWT
                String token = Jwts.builder()
                        .setSubject(user.getCredentials().getEmail())
                        .setIssuedAt(new Date())
                        .setExpiration(new Date(System.currentTimeMillis() + 86400000)) // Token válido por 1 día
                        .signWith(KEY, SignatureAlgorithm.HS256)
                        .compact();

                LoginResponse response = new LoginResponse("Login successful", token);
                return ResponseEntity.ok(response);
            }
        }

        throw new RuntimeException("Invalid email or password.");
    }

    // Cierre de sesión (se gestiona principalmente en el cliente)
    @Override
    public void logout() {

    }

    // Seguir a un usuario
    @Override
    public void followUser(ObjectId userId, ObjectId followUserId) {
        UsersModel user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        UsersModel followUser = usersRepository.findById(followUserId)
                .orElseThrow(() -> new RuntimeException("User to follow not found"));

        // Agregar el followUserId a la lista de "following" del usuario
        if (!user.getFollowing().contains(followUserId)) {
            user.getFollowing().add(followUserId);
            usersRepository.save(user);
        }

        // Agregar el userId a la lista de "followers" del usuario seguido
        if (!followUser.getFollowers().contains(userId)) {
            followUser.getFollowers().add(userId);
            usersRepository.save(followUser);
        }
    }

    // Dejar de seguir a un usuario
    @Override
    public void unfollowUser(ObjectId userId, ObjectId unfollowUserId) {
        UsersModel user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        UsersModel unfollowUser = usersRepository.findById(unfollowUserId)
                .orElseThrow(() -> new RuntimeException("User to unfollow not found"));

        // Remover el unfollowUserId de la lista de "following" del usuario
        user.getFollowing().remove(unfollowUserId);
        usersRepository.save(user);

        // Remover el userId de la lista de "followers" del usuario dejado de seguir
        unfollowUser.getFollowers().remove(userId);
        usersRepository.save(unfollowUser);
    }

    // Ver los seguidores de un usuario
    @Override
    public List<ObjectId> viewFollowers(ObjectId userId) {
        UsersModel user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFollowers();
    }

    // Ver los usuarios que un usuario sigue
    @Override
    public List<ObjectId> viewFollowing(ObjectId userId) {
        UsersModel user = usersRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        return user.getFollowing();
    }

    @Override
    public List<UsersModel> BooksByUser(String userName) {
        List<UsersModel> res = usersRepository.BooksByUser(userName);
        return res;
    }

    @Override
    public List<UsersModel> NonAcceptedExchanges(String Username) {
        List<UsersModel> res = usersRepository.NonAcceptedExchanges(Username);
        return res;
    }
}
