package backend.bd_proyect.Controller;

import backend.bd_proyect.DTOs.PrivateMessageDTO;
import backend.bd_proyect.Model.PrivateMessages.PrivateMessagesModel;
import backend.bd_proyect.Service.IPrivMessageService;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/messages")
public class PrivateMessageController {

    @Autowired
    private IPrivMessageService privMessageService;

    @PostMapping("/send")
    public ResponseEntity<String> sendPrivateMessage(@RequestBody PrivateMessageDTO privateMessageDTO) {
        String message = privMessageService.sendPrivateMessage(privateMessageDTO);
        return ResponseEntity.ok(message);
    }

@GetMapping("/view")
public List<PrivateMessagesModel> viewPrivateMessages(
        @RequestParam(required = false) String userId,
        @RequestParam(required = false) String idTransmitter,
        @RequestParam(required = false) String idReceiver) {

    ObjectId userObjectId = userId != null ? new ObjectId(userId) : null;
    ObjectId transmitterObjectId = idTransmitter != null ? new ObjectId(idTransmitter) : null;
    ObjectId receiverObjectId = idReceiver != null ? new ObjectId(idReceiver) : null;

    return privMessageService.viewPrivateMessages(userObjectId, transmitterObjectId, receiverObjectId);
}

}
