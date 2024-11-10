package backend.bd_proyect.DTOs;

import backend.bd_proyect.Model.PrivateMessages.AttachmentPrivMessage;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PrivateMessageDTO {
    private String idTransmitter;
    private String idReceiver;    
    private String messageContext; 
    private List<AttachmentPrivMessage> attachments;  
}
