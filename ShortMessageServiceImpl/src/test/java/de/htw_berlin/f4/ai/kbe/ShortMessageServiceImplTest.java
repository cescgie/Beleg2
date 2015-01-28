package de.htw_berlin.f4.ai.kbe;

import de.htw_berlin.aStudent.model.MessageIntern;

import de.htw_berlin.aStudent.model.TopicIntern;
import de.htw_berlin.aStudent.model.UserIntern;
import de.htw_berlin.aStudent.repository.*;

import static org.mockito.Mockito.*;

import de.htw_berlin.aStudent.service.AnApplicationService;
import de.htw_berlin.aStudent.service.AnApplicationServiceImpl;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.Message;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.ShortMessageService;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.ShortMessageServiceImpl;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ShortMessageServiceImplTest
{
    private MessageRepository messageRepository;
    private TopicRepository topicRepository;
    private UserRepository userRepository;
    private AnApplicationService anApplicationService;
    private ShortMessageService shortMessageService;

    protected final static String userName = "mustermann";
    protected final static String city = "Berlin";
    protected final static String topic = "Entwurfsmuster";

    @Before
    public void setUp()
    {
    }

    @Before
	public void prepareEnvironment(){	
		
    	messageRepository = mock(MessageRepository.class);
        topicRepository = mock(TopicRepository.class);
        userRepository = mock(UserRepository.class);
        shortMessageService = new ShortMessageServiceImpl();
	}
    
    @Test(expected=NullPointerException.class)
	public void testGetMessageByTopicNullPointer(){
		String topic = null;
		shortMessageService.getMessageByTopic(topic, null);
	}
    
    /*
    @Test(expected=IllegalArgumentException.class)
	public void testGetMessageByTopicIllegalArgumentException(){
		String topic = "faketopic-faketopic";
		shortMessageService.getMessageByTopic(topic, null);
	}*/
    
    @Test
    public void testGetMessageByTopic()
    {
        UserIntern user = new UserIntern(userName, city);
        TopicIntern topicIntern = new TopicIntern(user.getName(), topic);

        String messageContent = RandomStringUtils.random(20);
        Long messageId = 1L;
        MessageIntern message = new MessageIntern(messageId, new Date(), user, topic, messageContent, true);

        String responseContent0_1 = RandomStringUtils.random(25);
        String responseContent0_2 = RandomStringUtils.random(22);

        Long responseId0_1 = 2L;
        Long responseId0_2 = 3L;
        MessageIntern answer0_1 = new MessageIntern(responseId0_1, new Date(), user, topic, responseContent0_1, false, messageId);
        MessageIntern answer0_2 = new MessageIntern(responseId0_2, new Date(), user, topic, responseContent0_2, false, messageId);
       
        String messageContent1 = RandomStringUtils.random(25);
        String responseContent1_1 = RandomStringUtils.random(25);
        Long messageId1 = 5L;
        Long responseId1_1 = 6L;

        Date d = new Date();

        try
        {
            Thread.sleep(500);
        } catch (InterruptedException e)
        {
            e.printStackTrace();
        }


        MessageIntern message1 = new MessageIntern(messageId1, new Date(), user, topic, messageContent1, true);
        MessageIntern answer1_1 = new MessageIntern(responseId1_1, new Date(), user, topic, responseContent1_1, false, messageId1);
     
        List<UserIntern> userList = new ArrayList<UserIntern>();
        userList.add(user);

        List<TopicIntern> topicList = new ArrayList<TopicIntern>();
        topicList.add(topicIntern);

        List<MessageIntern> mList = new ArrayList<MessageIntern>();
        mList.add(message);
        mList.add(message1);
        mList.add(answer0_1);
        mList.add(answer0_2);
        mList.add(answer1_1);

        when(messageRepository.findOne(1L)).thenReturn(message);
        when(messageRepository.findOne(2L)).thenReturn(answer0_1);
        when(messageRepository.findOne(3L)).thenReturn(answer0_2);
        when(messageRepository.findOne(5L)).thenReturn(message1);
        when(messageRepository.findOne(6L)).thenReturn(answer1_1);

        when(messageRepository.findAll()).thenReturn(mList);

        when(userRepository.findAll()).thenReturn(userList);

        when(topicRepository.findOne(1L)).thenReturn(topicIntern);
        when(topicRepository.findAll()).thenReturn(topicList);

        anApplicationService = new AnApplicationServiceImpl(messageRepository, userRepository, topicRepository);
        shortMessageService = new ShortMessageServiceImpl(anApplicationService);

        List<List<Message>> messages = shortMessageService.getMessageByTopic(topic, null);

        assertEquals(2, messages.size());
        assertEquals(3, messages.get(0).size());
        assertEquals(2, messages.get(1).size());
        assertEquals(messageId, messages.get(0).get(0).getMessageId());
        assertEquals(responseId0_1, messages.get(0).get(1).getMessageId());
        assertEquals(responseId0_2, messages.get(0).get(2).getMessageId());
        assertEquals(responseContent0_2, messages.get(0).get(2).getContent());
        assertEquals(messageId1, messages.get(1).get(0).getMessageId());
        assertEquals(responseId1_1, messages.get(1).get(1).getMessageId());
        assertEquals(responseContent1_1, messages.get(1).get(1).getContent());


        messages = shortMessageService.getMessageByTopic(topic, d);
        assertEquals(1, messages.size());
        assertEquals(2, messages.get(0).size());

    }
}
