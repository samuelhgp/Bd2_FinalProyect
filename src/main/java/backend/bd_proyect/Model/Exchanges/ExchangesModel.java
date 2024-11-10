package backend.bd_proyect.Model.Exchanges;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import com.fasterxml.jackson.annotation.JsonProperty;

import backend.bd_proyect.Model.Enum.ExchangeState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Document(collection = "Exchanges")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangesModel {
    @Id
    private ObjectId id;
    private ObjectId idRequester;
    private ObjectId idRequested;
    private List<BookExchange> bookRequested = new ArrayList<>();  // Lista de documentos integrados
    private List<BookExchange> booksOffered = new ArrayList<>();   // Lista de documentos integrados
    private ExchangeState state = ExchangeState.pending;  // Estado predeterminado en Pending
    private Date exchangeDate = new Date();  // Fecha asignada automáticamente
    private List<ExchangeStatus> statusHistory = new ArrayList<>(); // Historial de cambios de estado

    @JsonProperty("id")
    public String getIdAsString() {
        return id != null ? id.toHexString() : null;
    }

    @JsonProperty("bookRequested")
    public List<String> getBookRequestedAsString() {
        return bookRequested != null ? bookRequested.stream().map(BookExchange::toString).collect(Collectors.toList()) : null;
    }

    @JsonProperty("booksOffered")
    public List<String> getBooksOfferedAsString() {
        return booksOffered != null ? booksOffered.stream().map(BookExchange::toString).collect(Collectors.toList()) : null;
    }

    // Método para agregar un cambio de estado al historial
    public void addStatusHistory(ExchangeState newState) {
        this.statusHistory.add(new ExchangeStatus(newState));
    }
}
