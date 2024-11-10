package backend.bd_proyect.Service;

import backend.bd_proyect.DTOs.PrivateMessageDTO;
import backend.bd_proyect.Model.PrivateMessages.PrivateMessagesModel;
import org.bson.types.ObjectId;

import java.util.List;

public interface IPrivMessageService {
    String sendPrivateMessage(PrivateMessageDTO privateMessageDTO);

    // Firma del método para ver mensajes entre dos usuarios con verificación de permisos
    List<PrivateMessagesModel> viewPrivateMessages(ObjectId userId, ObjectId idTransmitter, ObjectId idReceiver);

    // Firma del método para ver todos los mensajes de un usuario
    List<PrivateMessagesModel> viewAllUserMessages(ObjectId userId);
}
