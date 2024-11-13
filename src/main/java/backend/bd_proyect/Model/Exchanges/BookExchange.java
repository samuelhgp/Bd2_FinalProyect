package backend.bd_proyect.Model.Exchanges;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bson.types.ObjectId;

import backend.bd_proyect.Model.Enum.ExchangeState;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookExchange {
    private ObjectId idBook;
    private ExchangeState state;  // Enum 

    @Override
    public String toString() {
        return "Book ID: " + idBook + ", State: " + state;
    }
}
