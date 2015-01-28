package de.htw_berlin.f4.ai.kbe.kurznachrichten;

import java.util.List;

import de.htw_berlin.f4.ai.kbe.appconfig.AppConfig;
import de.htw_berlin.aStudent.model.MessageIntern;
import de.htw_berlin.aStudent.model.TopicIntern;
import de.htw_berlin.aStudent.model.UserIntern;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class App {
	public static void main( String[] args ) throws AuthorizationException
    {
		System.out.println("==++++TEST++++==");
		ApplicationContext context1 = new AnnotationConfigApplicationContext(AppConfig.class);
		//ApplicationContext context1 = new ClassPathXmlApplicationContext("config.xml");
		ShortMessageServiceImpl sms = (ShortMessageServiceImpl) context1.getBean("shortMessageServiceImpl");
		
		String user = "Testuser";
        String topic = "Testtopic";
        String testMessage = "TestMessage";
        //Long originId = 0L;
        
		deleteAll(sms);
		sms.createUser(user, "Berlin");
        sms.createTopic(user, topic);
        sms.createMessage(user, testMessage, topic);
        printListSizes(sms, topic);
        //sms.deleteMessage(user, id);
        //printListSizes(sms, topic);
    }
	private static void deleteAll(ShortMessageServiceImpl sms)
    {
        sms.deleteAllMessages();
        sms.deleteAllTopics();
        sms.deleteAllUsers();
    }
	private static void printListSizes(ShortMessageServiceImpl sms, String topic)
    {
        int i = 1;
        for (List<Message> mlist : sms.getMessageByTopic(topic, null))
        {
            System.out.println("List " + i + "; Size: " + mlist.size());
            i++;
        }
    }
}
