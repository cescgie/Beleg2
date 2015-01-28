package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.MessageIntern;
import java.util.List;

public interface MessageRepository
{
    public void delete(MessageIntern entity);

    public void deleteById(Long entityId);

    public Integer getAvailableMessageCount();
    
    public MessageIntern findOne(Long id);
    
    public List<MessageIntern> findAll();
    
    public void save(MessageIntern entity);
   
    public MessageIntern update(MessageIntern entity);

    public void deleteAllMessages();
}