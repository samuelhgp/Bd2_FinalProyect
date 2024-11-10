package backend.bd_proyect.Model.Exchanges;

import backend.bd_proyect.Model.Enum.ExchangeState;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ExchangeStatus {
    private Date date; // Fecha de cambio
    private ExchangeState state; // Estado del intercambio

    public ExchangeStatus(ExchangeState state) {
        this.date = new Date();
        this.state = state;
    }
}