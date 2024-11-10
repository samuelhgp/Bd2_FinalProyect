package backend.bd_proyect.Service;

import backend.bd_proyect.DTOs.PrivateMessageDTO;
import backend.bd_proyect.Model.PrivateMessages.PrivateMessagesModel;
import backend.bd_proyect.Repository.PrivMessageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.bson.types.ObjectId;

import java.util.Date;
import java.util.List;

@Service
public class PrivateMessageServiceImpl implements IPrivMessageService {

    @Autowired
    private PrivMessageRepository privMessageRepository;

    @Override
    public String sendPrivateMessage(PrivateMessageDTO privateMessageDTO) {
        PrivateMessagesModel privateMessage = new PrivateMessagesModel();
        privateMessage.setIdReceiver(new ObjectId(privateMessageDTO.getIdReceiver()));
        privateMessage.setIdTransmitter(new ObjectId(privateMessageDTO.getIdTransmitter()));
        privateMessage.setMessageContext(privateMessageDTO.getMessageContext());
        privateMessage.setAttachments(privateMessageDTO.getAttachments());
        privateMessage.setDate(new Date());

        privMessageRepository.save(privateMessage);

        return "El mensaje privado se ha enviado con éxito.";
    }

    @Override
    public List<PrivateMessagesModel> viewPrivateMessages(ObjectId userId, ObjectId idTransmitter, ObjectId idReceiver) {
        // Verificar si el usuario tiene permiso para ver estos mensajes
        if (!userId.equals(idTransmitter) && !userId.equals(idReceiver)) {
            throw new SecurityException("No tienes permisos para visualizar estos mensajes.");
        }

        // Consultar los mensajes entre los usuarios
        return privMessageRepository.findByIdTransmitterAndIdReceiver(idTransmitter, idReceiver);
    }

    @Override
    public List<PrivateMessagesModel> viewAllUserMessages(ObjectId userId) {
        // Consultar todos los mensajes en los que el usuario es receptor o emisor
        return privMessageRepository.findByIdTransmitterOrIdReceiver(userId, userId);
    }
}

