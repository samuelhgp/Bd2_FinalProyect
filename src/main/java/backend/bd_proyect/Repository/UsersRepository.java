package backend.bd_proyect.Repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import backend.bd_proyect.Model.Users.UsersModel;

@Repository
public interface UsersRepository extends MongoRepository<UsersModel, ObjectId> {
    Optional<UsersModel> findByCredentials_Email(String email);

    @Aggregation(pipeline = {
        "[ { $lookup: {from: 'Books', localField: '_id', foreignField: 'idUser', as: 'Books'}}," +
        "{$match: {'full_name':'userName'}," + 
        "{$project: { full_name: 1, cantBooks: { $size: '$Books' }, libros: '$Books'  } } ]"
    })
    List<UsersModel>BooksByUser(String userName);

    @Aggregation(pipeline = {
    "[{ $match: { full_name: 'Username' } }," +
    "{$lookup: { from: 'Exchanges', localField: '_id', foreignField: 'idRequester', as: 'intercambios'}}," +
    "{ $unwind: { path: '$intercambios' } }," +
    "{$match: {'intercambios.bookRequested.state':'pending'}}]"
    })
    List<UsersModel>NonAcceptedExchanges(String Username);
}
