package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.MessageIntern;

import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import java.math.BigInteger;


// Implementierung für die Klasse
@Repository
public class MessageRepositoryImpl extends AbstractRepository<MessageIntern> implements MessageRepository
{

    public MessageRepositoryImpl()    {
        setClazz(MessageIntern.class);
    }
    
    @Override
    public Integer getAvailableMessageCount() {
        Query q = entityManager.createNativeQuery("SELECT COUNT (*) FROM messageintern");
        BigInteger i = (BigInteger) q.getSingleResult();
        return i.intValue();
    }
    
   
    @Override
    public void deleteAllMessages() {
        Query q = entityManager.createNativeQuery("DELETE FROM messageintern");
        q.executeUpdate();
    }
}
