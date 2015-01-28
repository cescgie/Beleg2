package de.htw_berlin.aStudent.service;

import de.htw_berlin.f4.ai.kbe.kurznachrichten.AuthorizationException;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.Message;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.User;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.Set;


public interface AnApplicationService
{
    public void createUser(String name, String city) throws IllegalArgumentException;

    public Long createMessage(Date date, String content, String topic, String user);

    public Long respondToMessage(Date date, String content, String user, long messageId);

    public void createTopic(String userName, String topicName);

    public User getUser(String user);

    public Message getMessage(Long messageId);

    public Set<User> getAllUsers();

    public List<Message> getAllMessages();

    public void deleteMessage(String userName, Long messageId);

    public Set<String> getTopics();

    public List<List<Message>> getMessageByTopic(String topic, Date since);

    public void deleteUser(String userName) throws IllegalArgumentException;

    public void deleteAllUsers();

    public void deleteAllMessages();

    public void deleteAllTopics();
}
