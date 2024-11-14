package backend.bd_proyect.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.fasterxml.jackson.annotation.JsonProperty;

import backend.bd_proyect.Model.Enum.BookState;
import backend.bd_proyect.Model.Enum.Condition;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Books")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BooksModel {
    @Id
    private ObjectId id;
    private String title;
    private String author;
    private List<ObjectId> genre = new ArrayList<>();  // array
    private Condition condition;  // Enum
    private BookState state;
    private String cover_image;
    private List<String> comments = new ArrayList<>();
    private ObjectId idUser;  // fk

    @JsonProperty("id")
    public String getIdAsString() {
        return id != null ? id.toHexString() : null;
    }

    @JsonProperty("genre")
    public List<String> getGenreAsString() {
        return genre != null ? genre.stream().map(ObjectId::toHexString).collect(Collectors.toList()) : null;
    }
}