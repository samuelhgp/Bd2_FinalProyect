package backend.bd_proyect.Controller;

import backend.bd_proyect.DTOs.CommentDTO;
import backend.bd_proyect.Model.Comments.CommentsModel;
import backend.bd_proyect.Service.ICommentsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/comments")
public class CommentsController {

    @Autowired
    private ICommentsService commentsService;

    @PostMapping("/add")
    public ResponseEntity<String> addComment(@RequestBody CommentDTO commentDTO) {
        String message = commentsService.addComment(commentDTO);
        return ResponseEntity.ok(message);
    }

    @GetMapping("/view/{idBook}")
    public List<CommentsModel> viewComments(@PathVariable String idBook) {
        return commentsService.viewComments(idBook);
    }
}
