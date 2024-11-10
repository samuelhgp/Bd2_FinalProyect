package backend.bd_proyect.Repository;

import backend.bd_proyect.Model.Comments.CommentsModel;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import org.bson.types.ObjectId;
import java.util.List;

@Repository
public interface CommentsRepository extends MongoRepository<CommentsModel, String> {
    List<CommentsModel> findByIdBook(ObjectId idBook);
}
