package backend.bd_proyect.Repository;

import backend.bd_proyect.Model.PrivateMessages.PrivateMessagesModel;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PrivMessageRepository extends MongoRepository<PrivateMessagesModel, String> {
    // Método para obtener mensajes enviados entre dos usuarios en una dirección (Transmitter -> Receiver)
    List<PrivateMessagesModel> findByIdTransmitterAndIdReceiver(ObjectId idTransmitter, ObjectId idReceiver);

    // Método para obtener mensajes enviados en la dirección opuesta (Receiver -> Transmitter)
    List<PrivateMessagesModel> findByIdReceiverAndIdTransmitter(ObjectId idReceiver, ObjectId idTransmitter);

    // Método para obtener todos los mensajes donde el usuario es transmisor o receptor
    List<PrivateMessagesModel> findByIdTransmitterOrIdReceiver(ObjectId idTransmitter, ObjectId idReceiver);
}
