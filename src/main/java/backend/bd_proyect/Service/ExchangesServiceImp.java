package backend.bd_proyect.Service;

import backend.bd_proyect.DTOs.ExchangeRequestDTO;
import backend.bd_proyect.Model.Exchanges.BookExchange;
import backend.bd_proyect.Model.Exchanges.ExchangeStatus;
import backend.bd_proyect.Model.Exchanges.ExchangesModel;
import backend.bd_proyect.Model.BooksModel;
import backend.bd_proyect.Model.Enum.BookState;
import backend.bd_proyect.Model.Enum.ExchangeState;
import backend.bd_proyect.Repository.BooksRepository;
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

    @Autowired
    private BooksRepository booksRepository;  // Repositorio para actualizar los estados de los libros

    // Método para enviar una solicitud de intercambio
    @Override
    public String sendExchangeRequest(ExchangeRequestDTO exchangeRequestDTO) {
        ExchangesModel exchange = new ExchangesModel();
        exchange.setIdRequester(new ObjectId(exchangeRequestDTO.getRequesterId()));
        exchange.setIdRequested(new ObjectId(exchangeRequestDTO.getReceiverId()));
    
        exchange.setBooksOffered(exchangeRequestDTO.getBooksOfferedIds().stream()
                .map(bookId -> new BookExchange(new ObjectId(bookId), ExchangeState.pending))
                .collect(Collectors.toList()));
        exchange.setBookRequested(List.of(new BookExchange(new ObjectId(exchangeRequestDTO.getBookRequestedId()), ExchangeState.pending)));
    
        exchange.setExchangeDate(new Date());
        exchange.setState(ExchangeState.pending);  // Estado inicial en `pending`
        exchange.addStatusHistory(ExchangeState.pending);  // Historial del estado inicial
        exchangesRepository.save(exchange);
    
        return "Solicitud de intercambio enviada con éxito 👌🏻";
    }

    // Método para aceptar una solicitud de intercambio
    @Override
    public String acceptExchangeRequest(String exchangeId) {
        ExchangesModel exchange = exchangesRepository.findById(new ObjectId(exchangeId))
                .orElseThrow(() -> new RuntimeException("Intercambio no encontrado"));
    
        // Validar que el estado actual del intercambio sea `pending`
        if (exchange.getState() != ExchangeState.pending) {
            throw new IllegalStateException("No se puede aceptar un intercambio que no está pendiente");
        }
    
        // Cambiar el estado del intercambio a `accepted`
        exchange.setState(ExchangeState.accepted);
        exchange.addStatusHistory(ExchangeState.accepted);
    
        // Cambiar el estado de disponibilidad de los libros solicitados en `Books` a `not_available`
        for (BookExchange book : exchange.getBookRequested()) {
            BooksModel bookModel = booksRepository.findById(book.getIdBook())
                    .orElseThrow(() -> new RuntimeException("Libro no encontrado en libros solicitados"));
            bookModel.setState(BookState.not_available);  // Cambiar a `not_available`
            booksRepository.save(bookModel);
        }
    
        // Cambiar el estado de disponibilidad de los libros ofrecidos en `Books` a `not_available`
        for (BookExchange book : exchange.getBooksOffered()) {
            BooksModel bookModel = booksRepository.findById(book.getIdBook())
                    .orElseThrow(() -> new RuntimeException("Libro no encontrado en libros ofrecidos"));
            bookModel.setState(BookState.not_available);  // Cambiar a `not_available`
            booksRepository.save(bookModel);
        }
    
        // Guardar el intercambio con el estado actualizado
        exchangesRepository.save(exchange);
    
        return "El intercambio ha sido aprobado exitosamente.";
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
