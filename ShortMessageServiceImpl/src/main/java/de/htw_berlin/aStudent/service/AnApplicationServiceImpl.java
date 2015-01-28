package de.htw_berlin.aStudent.service;

import de.htw_berlin.aStudent.model.MessageIntern;
import de.htw_berlin.aStudent.model.TopicIntern;
import de.htw_berlin.aStudent.model.UserIntern;
import de.htw_berlin.aStudent.repository.MessageRepository;
import de.htw_berlin.aStudent.repository.TopicRepository;
import de.htw_berlin.aStudent.repository.UserRepository;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.Message;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
public class AnApplicationServiceImpl implements AnApplicationService
{

    @Autowired
    MessageRepository messageRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    TopicRepository topicRepository;
 
    public AnApplicationServiceImpl(MessageRepository messageRepository2,
			UserRepository userRepository2, TopicRepository topicRepository2) {
    	this.messageRepository = messageRepository2;
        this.userRepository = userRepository2;
        this.topicRepository = topicRepository2;	
    }
   
    public AnApplicationServiceImpl(){
    	
    }
    
	@Override
    @Transactional
    public void createUser(String name, String city)
    {
        UserIntern u = new UserIntern(name, city);
        userRepository.save(u);
    }

    @Override
    @Transactional
    public Long createMessage(Date date, String content, String topic, String user)
    {
        User u = getUser(user);
        long id = getNewMessageId();
        MessageIntern m = new MessageIntern(id, date, u, topic, content, true);
        messageRepository.save(m);
        return id;
    }

    @Override
    @Transactional
    public Long respondToMessage(Date date, String content, String user, long originId)
    {
        User u = getUser(user);
        Message msg = getMessage(originId);
        long id = getNewMessageId();
        MessageIntern m = new MessageIntern(id, date, u, msg.getTopic(), content, false, originId);
        messageRepository.save(m);
        return id;
    }

    @Override
    @Transactional
    public void createTopic(String topicName, String userName)
    {
        topicRepository.save(new TopicIntern(topicName, userName));
    }

    @Override
    @Transactional(readOnly = true)
    public User getUser(String user)
    {
        for (User u : getAllUsers())
        {
            if (u.getName().equals(user))
                return u;
        }

        return null;
    }

    @Override
    @Transactional(readOnly = true)
    public Message getMessage(Long messageId)
    {
        return messageRepository.findOne(messageId);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<User> getAllUsers()
    {
        List<UserIntern> uI = userRepository.findAll();
        Set<User> users = new HashSet<User>(uI);

        return users;
    }

    @Override
    @Transactional(readOnly = true)
    public List<Message> getAllMessages()
    {
        List<MessageIntern> msgsI = messageRepository.findAll();
        List<Message> msgs = new ArrayList<Message>();

        for (MessageIntern m : msgsI)
        {
            msgs.add(m);
        }

        return msgs;
    }

    @Override
    @Transactional
    public void deleteMessage(String userName, Long messageId)
    {
        MessageIntern m = messageRepository.findOne(messageId);

        List<List<Message>> mLists = getMessageByTopic(m.getTopic(), null);

        for (List<Message> msgs : mLists)
        {
            if (msgs.get(0).getMessageId().equals(m.getMessageId()))
            {
                for (Message msg : msgs)
                {
                    messageRepository.deleteById(msg.getMessageId());
                }

                return;
            }
        }
        messageRepository.delete(m);
    }

    @Override
    @Transactional(readOnly = true)
    public Set<String> getTopics()
    {
        List<TopicIntern> tI = topicRepository.findAll();
        Set<String> topics = new HashSet<String>();

        for (TopicIntern t : tI)
        {
            topics.add(t.getTopic());
        }

        return topics;
    }

    @Override
    @Transactional(readOnly = true)
    public List<List<Message>> getMessageByTopic(String topic, Date since)
    {
        List<List<Message>> mLists = new ArrayList<List<Message>>(); //Alle Nachrichten
        List<List<Message>> mLists2 = new ArrayList<List<Message>>(); // gefiltert nach topic und datum
        List<MessageIntern> msgs = messageRepository.findAll();
        int i = 0;
        for (MessageIntern msg : msgs)
        {
            if (msg.isOrigin())
            {
                long id = msg.getMessageId();
                mLists.add(new ArrayList<Message>());
                mLists.get(i).add(msg);

                for (MessageIntern m : msgs)
                {
                    if (m.getOriginId() == id)
                    {
                        mLists.get(i).add(m);
                    }
                }
                i++;
            }

        }

        for (List<Message> mList : mLists)
        {
            if (mList.get(0).getTopic().equals(topic))
            {
                if (since == null || mList.get(0).getDate().after(since))
                {
                    mLists2.add(mList);
                }
            }
        }

        return mLists2;
    }

    @Override
    @Transactional
    public void deleteUser(String userName)
    {
        User u = getUser(userName);

        for (UserIntern ui : userRepository.findAll())
        {
            if (ui.getName().equals(u.getName()))
                userRepository.delete(ui);
        }
    }

    @Override
    @Transactional
    public void deleteAllUsers()
    {
        userRepository.deleteAllUsers();
    }

    @Override
    @Transactional
    public void deleteAllMessages()
    {
        messageRepository.deleteAllMessages();
    }

    @Override
    @Transactional
    public void deleteAllTopics()
    {
        topicRepository.deleteAllTopics();
    }

    // ---------------- Hilfsmethoden ------------------------

    private long getNewMessageId()
    {
        long id = -1;
        List<MessageIntern> msgs = messageRepository.findAll();

        for (MessageIntern m : msgs)
        {
            if (m.getMessageId() > id)
            {
                id = m.getMessageId();
            }
        }

        return (id + 1);
    }
}
