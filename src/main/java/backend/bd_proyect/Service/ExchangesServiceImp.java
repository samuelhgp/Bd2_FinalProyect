package backend.bd_proyect.Service;

import backend.bd_proyect.DTOs.ExchangeRequestDTO;
import backend.bd_proyect.Model.Exchanges.BookExchange;
import backend.bd_proyect.Model.Exchanges.ExchangeStatus;
import backend.bd_proyect.Model.Exchanges.ExchangesModel;
import backend.bd_proyect.Model.Enum.BookState;
import backend.bd_proyect.Model.Enum.ExchangeState;
import backend.bd_proyect.Repository.ExchangesRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ExchangesServiceImp implements IExchangesService {

    @Autowired
    private ExchangesRepository exchangesRepository;

    @Override
    public String sendExchangeRequest(ExchangeRequestDTO exchangeRequestDTO) {
        ExchangesModel exchange = new ExchangesModel();
        exchange.setIdRequester(new ObjectId(exchangeRequestDTO.getRequesterId()));
        exchange.setIdRequested(new ObjectId(exchangeRequestDTO.getReceiverId()));
        exchange.setBooksOffered(exchangeRequestDTO.getBooksOfferedIds().stream()
                .map(bookId -> new BookExchange(new ObjectId(bookId), BookState.pending))
                .collect(Collectors.toList()));
        exchange.setBookRequested(List.of(new BookExchange(new ObjectId(exchangeRequestDTO.getBookRequestedId()), BookState.pending)));
        exchange.setExchangeDate(new Date());

        // Añadir el estado inicial al historial
        exchange.addStatusHistory(ExchangeState.pending);

        exchangesRepository.save(exchange);
        return "Solicitud enviada con éxito";
    }

    @Override
    public ExchangesModel acceptExchangeRequest(String exchangeId) {
        ExchangesModel exchange = exchangesRepository.findById(new ObjectId(exchangeId))
                .orElseThrow(() -> new RuntimeException("Intercambio no encontrado"));

        if (exchange.getState() != ExchangeState.pending) {
            throw new IllegalStateException("No se puede aceptar un intercambio que no está pendiente");
        }

        exchange.setState(ExchangeState.accepted);
        exchange.addStatusHistory(ExchangeState.accepted); // Agregar al historial
        return exchangesRepository.save(exchange);
    }

    @Override
    public ExchangesModel declineExchangeRequest(String exchangeId) {
        ExchangesModel exchange = exchangesRepository.findById(new ObjectId(exchangeId))
                .orElseThrow(() -> new RuntimeException("Intercambio no encontrado"));

        if (exchange.getState() != ExchangeState.pending) {
            throw new IllegalStateException("No se puede rechazar un intercambio que no está pendiente");
        }

        exchange.setState(ExchangeState.denied);
        exchange.addStatusHistory(ExchangeState.denied); // Agregar al historial
        return exchangesRepository.save(exchange);
    }

    @Override
    public List<ExchangeStatus> trackExchange(String exchangeId) {
        ExchangesModel exchange = exchangesRepository.findById(new ObjectId(exchangeId))
                .orElseThrow(() -> new RuntimeException("Intercambio no encontrado"));
        return exchange.getStatusHistory(); // Devolver historial de estados
    }
}
