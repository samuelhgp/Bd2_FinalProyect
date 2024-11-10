package backend.bd_proyect.Service;

import backend.bd_proyect.DTOs.CommentDTO;
import backend.bd_proyect.Model.Comments.CommentsModel;
import backend.bd_proyect.Repository.BooksRepository;
import backend.bd_proyect.Repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentsServiceImp implements ICommentsService {

    @Autowired
    private CommentsRepository commentsRepository;

    @Autowired
    private BooksRepository booksRepository;  

    @Override
    public String addComment(CommentDTO commentDTO) {
        CommentsModel comment = new CommentsModel();
        comment.setIdUser(new ObjectId(commentDTO.getIdUser()));
        comment.setIdBook(new ObjectId(commentDTO.getIdBook()));
        comment.setDescription(commentDTO.getDescription());
        comment.setAttachments(commentDTO.getAttachments());
        comment.setDate(new Date());

        commentsRepository.save(comment);

        booksRepository.findById(comment.getIdBook()).ifPresent(book -> {
            if (book.getComments() == null) {
                book.setComments(new ArrayList<>()); 
            }
            book.getComments().add(comment.getDescription()); 
            booksRepository.save(book); 
        });

        return "El comentario se ha agregado con éxito.";
    }
    
    // Método para ver comentarios en una publicación de libro
    @Override
    public List<CommentsModel> viewComments(String idBook) {
        return commentsRepository.findByIdBook(new ObjectId(idBook));
    }
}
