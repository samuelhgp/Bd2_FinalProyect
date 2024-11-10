package backend.bd_proyect.Repository;

import backend.bd_proyect.Model.Exchanges.ExchangesModel;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ExchangesRepository extends MongoRepository<ExchangesModel, ObjectId> {
    
}
