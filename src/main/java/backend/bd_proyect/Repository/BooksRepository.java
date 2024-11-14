package backend.bd_proyect.Repository;

import backend.bd_proyect.Model.BooksModel;
import backend.bd_proyect.Model.Enum.BookState;
import backend.bd_proyect.Model.Enum.Condition;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Aggregation;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BooksRepository extends MongoRepository<BooksModel, ObjectId> {
    
    List<BooksModel> findByTitleContainingIgnoreCase(String title);
    List<BooksModel> findByAuthorContainingIgnoreCase(String author);
    
    // Búsqueda por género (busca en la lista de ObjectId de géneros)
    List<BooksModel> findByGenreContaining(ObjectId genreId);

    // Filtrado exacto por género, autor, condición y estado del usuario
    List<BooksModel> findByAuthor(String author);
    List<BooksModel> findByCondition(Condition condition);
    List<BooksModel> findByIdUser(ObjectId idUser);
    // Filtrado exacto por estado del libro (enum BookState)
    List<BooksModel> findByState(BookState state);

    @Aggregation(pipeline = {
        "[ { $group: {_id: '$condition',count: { $sum: 1 } } } ]"
    })
    List<BooksModel>bookStates();

    @Aggregation(pipeline = {
        "[{ $match: { state: 'available' } }]," +
        "{ maxTimeMS: 60000, allowDiskUse: true })"
    })
    List<BooksModel>booksAvalibles();

    @Aggregation(pipeline = {
        "[{$lookup: {from: 'Comments',localField: '_id',foreignField: 'idBook',as: 'Comments'}}," +
        "{$project: {id: 1, title: 1, cantComments: { $size: '$Comments' }}}]"
    })
    List<BooksModel>allCommentsBooks();
    
    @Aggregation(pipeline = {
        "[{$project: {_id: 0 }}," +
        "{$sort: {comments: -1}}]"
    })
    List<BooksModel>BooksSortByComments();
}   
