package backend.bd_proyect.Service;

import java.util.List;

import backend.bd_proyect.DTOs.ExchangeRequestDTO;
import backend.bd_proyect.Model.Exchanges.ExchangeStatus;
import backend.bd_proyect.Model.Exchanges.ExchangesModel;

public interface IExchangesService {

    // Método para enviar una solicitud de intercambio
    String sendExchangeRequest(ExchangeRequestDTO exchangeRequestDTO);

    // Método para aceptar una solicitud de intercambio
    String acceptExchangeRequest(String exchangeId);

    // Método para rechazar una solicitud de intercambio
    ExchangesModel declineExchangeRequest(String exchangeId);

    List<ExchangeStatus> trackExchange(String exchangeId);

}
