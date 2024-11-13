package backend.bd_proyect.Controller;

import backend.bd_proyect.DTOs.ExchangeRequestDTO;
import backend.bd_proyect.Model.Exchanges.ExchangeStatus;
import backend.bd_proyect.Model.Exchanges.ExchangesModel;
import backend.bd_proyect.Service.IExchangesService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/exchanges")
public class ExchangesController {

    @Autowired
    private IExchangesService exchangesService;

    // Método para enviar una solicitud de intercambio
    @PostMapping("/send")
    public String sendExchangeRequest(@RequestBody ExchangeRequestDTO exchangeRequestDTO) {
        return exchangesService.sendExchangeRequest(exchangeRequestDTO);
    }

    // Método para aceptar una solicitud de intercambio
    @PutMapping("/accept/{exchangeId}")
    public String acceptExchangeRequest(@PathVariable String exchangeId) {
        return exchangesService.acceptExchangeRequest(exchangeId);
    }

    // Método para rechazar una solicitud de intercambio
    @PutMapping("/decline/{exchangeId}")
    public ExchangesModel declineExchangeRequest(@PathVariable String exchangeId) {
        return exchangesService.declineExchangeRequest(exchangeId);
    }

    // Nuevo endpoint para el seguimiento de intercambio
    @GetMapping("/track/{exchangeId}")
    public List<ExchangeStatus> trackExchange(@PathVariable String exchangeId) {
        return exchangesService.trackExchange(exchangeId);
    }

}
