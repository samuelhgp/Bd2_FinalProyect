package backend.bd_proyect.Model;

import java.util.ArrayList;
import java.util.List;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Categories")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoriesModel {
    @Id
    private ObjectId id;
    private String idCategorie;
    private String categorie;
    private List<String> subCategories = new ArrayList<>();  

    @JsonProperty("id")
    public String getIdAsString() {
        return id != null ? id.toHexString() : null;
    }
}