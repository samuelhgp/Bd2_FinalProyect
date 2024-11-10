package backend.bd_proyect.Model.Comments;

import backend.bd_proyect.Model.Enum.AttachmentType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Attachment {
    private AttachmentType type; // Enum con valores "image" y "pdf"
    private String file;

    @Override
    public String toString() {
        return "Type: " + type + ", File: " + file;
    }
}
