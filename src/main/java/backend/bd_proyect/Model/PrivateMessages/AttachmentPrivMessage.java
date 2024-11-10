package backend.bd_proyect.Model.PrivateMessages;

import backend.bd_proyect.Model.Enum.TypePrivMenssages;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AttachmentPrivMessage {

    private TypePrivMenssages type; 
    private String file; 

    @Override
    public String toString() {
        return "Type: " + type + ", File: " + file;
    }
}
