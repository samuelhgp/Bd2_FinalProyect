package backend.bd_proyect.Repository;

import backend.bd_proyect.Model.Users.UsersModel;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UsersRepository extends MongoRepository<UsersModel, ObjectId> {
    Optional<UsersModel> findByCredentials_Email(String email);
}
