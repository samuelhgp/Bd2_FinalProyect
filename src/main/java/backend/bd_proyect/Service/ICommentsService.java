package backend.bd_proyect.Service;

import java.util.List;

import backend.bd_proyect.DTOs.CommentDTO;
import backend.bd_proyect.Model.Comments.CommentsModel;

public interface ICommentsService {
    String addComment(CommentDTO commentDTO);
    List<CommentsModel> viewComments(String idBook); 
}