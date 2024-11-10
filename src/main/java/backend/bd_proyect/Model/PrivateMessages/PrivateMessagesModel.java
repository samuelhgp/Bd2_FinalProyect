package backend.bd_proyect.Model.PrivateMessages;

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

@Document(collection = "PrivateMessages")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivateMessagesModel {
    @Id
    private ObjectId id;
    private ObjectId idAnswer;
    private ObjectId idTransmitter;  // emisor
    private ObjectId idReceiver; // receptor
    private String messageContext; 
    private Date date;  
    private List<AttachmentPrivMessage> attachments = new ArrayList<>();

    @JsonProperty("id")
    public String getIdAsString() {
        return id != null ? id.toHexString() : null;
    }

    @JsonProperty("attachments")
    public List<String> getAttachmentsAsString() {
        return attachments != null ? attachments.stream().map(AttachmentPrivMessage::toString).collect(Collectors.toList()) : null;
    }
}
