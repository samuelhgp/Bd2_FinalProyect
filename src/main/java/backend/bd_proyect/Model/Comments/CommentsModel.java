package backend.bd_proyect.Model.Comments;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Comments")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CommentsModel {
    @Id
    private ObjectId id;
    private ObjectId idUser;       
    private ObjectId idBook;      
    private String description;
    private Date date;   
    
    private List<Attachment> attachments = new ArrayList<>();  // Lista de adjuntos

    // Método para convertir el ID a String en formato JSON
    @JsonProperty("id")
    public String getIdAsString() {
        return id != null ? id.toHexString() : null;
    }
    
    // Método para convertir la lista de attachments a String en formato JSON
    @JsonProperty("attachments")
    public List<String> getAttachmentsAsString() {
        return attachments != null ? attachments.stream().map(Attachment::toString).collect(Collectors.toList()) : null;
    }
}
