package backend.bd_proyect.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentResponseDTO {
    private String idUser;
    private Date date;
    private String description;
}
