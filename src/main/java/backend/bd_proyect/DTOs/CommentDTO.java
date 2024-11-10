package backend.bd_proyect.DTOs;

import backend.bd_proyect.Model.Comments.Attachment;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentDTO {
    private String idUser;  
    private String idBook;  
    private String description;
    private List<Attachment> attachments;  
}
