package backend.bd_proyect.Service;

import backend.bd_proyect.DTOs.CommentDTO;
import backend.bd_proyect.DTOs.CommentResponseDTO;
import backend.bd_proyect.Model.Comments.CommentsModel;
import backend.bd_proyect.Repository.BooksRepository;
import backend.bd_proyect.Repository.CommentsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
    public List<CommentResponseDTO> viewComments(String idBook) {
        // Obtén todos los comentarios para el libro usando el idBook
        List<CommentsModel> comments = commentsRepository.findByIdBook(new ObjectId(idBook));
    
        // Mapea los comentarios a CommentResponseDTO para que solo incluya los campos idUser, date y description
        return comments.stream()
                .map(comment -> new CommentResponseDTO(
                        comment.getIdUser().toHexString(),
                        comment.getDate(),
                        comment.getDescription()
                ))
                .collect(Collectors.toList());
    }
    
    @Override
    public String deleteComment(String commentId) {
        // Busca el comentario antes de intentar eliminarlo
        Optional<CommentsModel> commentOpt = commentsRepository.findById(commentId);
        if (commentOpt.isPresent()) {
            // Elimina el comentario de la colección Comments
            commentsRepository.deleteById(commentId);
    
            // También elimina el comentario de la lista de comentarios en la colección Books
            CommentsModel comment = commentOpt.get();
            booksRepository.findById(comment.getIdBook()).ifPresent(book -> {
                if (book.getComments() != null) {
                    book.getComments().remove(comment.getDescription());
                    booksRepository.save(book);
                }
            });
    
            return "El comentario ha sido eliminado exitosamente.";
        } else {
            return "Comentario no encontrado. No se realizó ninguna eliminación.";
        }
    }

}
