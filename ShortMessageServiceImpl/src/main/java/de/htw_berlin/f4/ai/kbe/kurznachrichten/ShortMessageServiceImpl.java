package de.htw_berlin.f4.ai.kbe.kurznachrichten;

import java.util.Date;

import java.util.List;
import java.util.Set;
import org.springframework.beans.factory.annotation.Autowired;
import de.htw_berlin.aStudent.service.AnApplicationService;
import org.springframework.stereotype.Service;

@Service("shortMessageServiceImpl")
public class ShortMessageServiceImpl implements ShortMessageService
{


    @Autowired
    AnApplicationService anApplicationService;
    
    public ShortMessageServiceImpl(AnApplicationService anApplicationService) {
        this.anApplicationService = anApplicationService;
	}
   
    public ShortMessageServiceImpl(){   	
    }
    
	@Override
    public Long createMessage(String userName, String message, String topic)
    {
        if (userName == null || message == null || topic == null)
            throw new NullPointerException("Keines der Argumente darf null sein.");
        if (message.length() < 10 || message.length() > 255)
            throw new IllegalArgumentException("Die Nachricht hat eine ungültige Länge.");
        if (!userExists(userName))
            throw new IllegalArgumentException("Der User existiert nicht");
        if (!topicExists(topic))
            throw new IllegalArgumentException("Das Thema existiert nicht.");

        return anApplicationService.createMessage(new Date(), message, topic, userName);
    }

    @Override
    public Long respondToMessage(String userName, String message,
                                 Long predecessor)
    {
        if (userName == null || message == null || predecessor == null)
            throw new NullPointerException("Keines der Argumente darf null sein.");
        if (message.length() < 10 || message.length() > 255)
            throw new IllegalArgumentException("Die Nachricht hat eine ungültige Länge.");
        if (!userExists(userName))
            throw new IllegalArgumentException("Der User existiert nicht");

        Message m = getMessageById(predecessor);
        if (m == null)
            throw new IllegalArgumentException("Der Vorgänger existiert nicht");
        if (!m.isOrigin())
            throw new IllegalArgumentException("Der Vorgänger ist keine Ursprungsnachricht.");

        return anApplicationService.respondToMessage(new Date(), message, userName, predecessor);
    }

    @Override
    public void deleteMessage(String userName, Long messageId)
            throws AuthorizationException
    {
        if (userName == null || messageId == null)
            throw new NullPointerException("Keines der Argumente darf null sein.");
        Message m = getMessageById(messageId);
        if (m == null)
            throw new IllegalArgumentException("Die Nachricht existiert nicht");
        if (!m.isOrigin())
            throw new IllegalArgumentException("Die Nachricht ist keine Ursprungsnachricht.");
        if (!userExists(userName))
            throw new IllegalArgumentException("Der Nutzer existiert nicht");
        if (!m.getUser().getName().equals(getUserByName(userName).getName()))
            throw new AuthorizationException(userName);

        anApplicationService.deleteMessage(userName, messageId);
    }

    @Override
    public void createTopic(String userName, String topic)
    {
        if (userName == null || topic == null)
            throw new NullPointerException("Keines der Argumente darf null sein.");
        if (!userExists(userName))
            throw new IllegalArgumentException("Der Nutzer existiert nicht.");
        if (topic.length() < 2 || topic.length() > 70)
            throw new IllegalArgumentException("Der Topicname hat eine ungültige Länge.");
        if (getTopics().contains(topic))
            throw new IllegalArgumentException("Ein Thema mit diesem Namen existiert bereits.");

        anApplicationService.createTopic(userName, topic);
    }

    @Override
    public Set<String> getTopics()
    {
        return anApplicationService.getTopics();
    }

    @Override
    public List<List<Message>> getMessageByTopic(
            String topic, Date since)
    {
        if (topic == null)
            throw new NullPointerException("Das Topic darf nicht Null sein.");
        if (!getTopics().contains(topic))
            throw new IllegalArgumentException("Das Thema existiert nicht.");

        return anApplicationService.getMessageByTopic(topic, since);
    }

    @Override
    public void createUser(String userName, String city)
    {
        // TODO implement correctly
        if (userName == null)
            throw new NullPointerException("Der Nutzername darf nicht null sein.");
        if (city == null)
            throw new NullPointerException("Der Stadtname darf nicht null sein.");
        if (userName.length() < 4 || userName.length() > 30)
            throw new IllegalArgumentException("Der Nutzername hat eine ungültig Länge.");
        if (userExists(userName))
            throw new IllegalArgumentException("Der Nutzer existiert bereits.");

        anApplicationService.createUser(userName, city);
        //dummy for test
        //anApplicationService.doSomeThing();
    }

    @Override
    public void deleteUser(String userName)
    {
        // Nicht in der Schnittstelle definiert aber trotzdem erforderlich!?
        if (userName == null)
            throw new NullPointerException("Der Nutzername darf nicht Null sein.");
        if (!userExists(userName))
            throw new IllegalArgumentException("Der Nutzer existiert nicht.");

        anApplicationService.deleteUser(userName);
    }

    @Override
    public Set<User> getUsers()
    {
        return anApplicationService.getAllUsers();
    }

    // --------- for testing reasons ------------------

    public void deleteAllMessages() {
        anApplicationService.deleteAllMessages();
    }

    public void deleteAllUsers() {
        anApplicationService.deleteAllUsers();
    }

    public void deleteAllTopics() {
        anApplicationService.deleteAllTopics();
    }


    // ---------------- Hilfsmethoden ------------------------

    private User getUserByName(String userName)
    {
        for (User u : getUsers())
        {
            if (userName.equals(u.getName()))
            {
                return u;
            }
        }
        return null;
    }

    private boolean userExists(String userName)
    {
        User u = getUserByName(userName);
        return (u != null);
    }

    private boolean topicExists(String topic)
    {
        for (String t : getTopics())
        {
            if (topic.equals(t))
            {
                return true;
            }
        }

        return false;
    }

    private Message getMessageById(Long id)
    {
        return anApplicationService.getMessage(id);
    }
}

