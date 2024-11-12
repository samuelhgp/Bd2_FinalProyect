package backend.bd_proyect.Service;

import java.util.List;

import backend.bd_proyect.DTOs.CommentDTO;
import backend.bd_proyect.DTOs.CommentResponseDTO;

public interface ICommentsService {
    String addComment(CommentDTO commentDTO);
    List<CommentResponseDTO> viewComments(String idBook);
    String deleteComment(String commentId);
}