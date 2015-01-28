package de.htw_berlin.aStudent.model;

import de.htw_berlin.f4.ai.kbe.kurznachrichten.Message;
import de.htw_berlin.f4.ai.kbe.kurznachrichten.User;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@Entity
@Table (name = "messageintern")
public class MessageIntern extends Message implements Serializable
{
    @Column
    private Long originId = -1l;
    
    @Id
    private Long messageId = 0l;
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column
    private Date date = null;
    
    @Column
    private String content = "";
    
    @Column
    private String topic = "";
    
    @ManyToOne
    @JoinColumn(name = "userintern")
    private UserIntern userintern = null;
    
    @Column
    private Boolean origin = false;


    public long getOriginId()
    {
        return originId;
    }

    public Date getDate()
    {
        return date;
    }

    public void setDate(Date date)
    {
        this.date = date;
    }

    public String getContent()
    {
        return content;
    }

    public void setContent(String content)
    {
        this.content = content;
    }

    public String getTopic()
    {
        return topic;
    }

    public void setTopic(String topic)
    {
        this.topic = topic;
    }

    public void setUser(User user)
    {
        this.userintern = (UserIntern) user;
    }

    public User getUser()
    {
        return userintern;
    }

    public Boolean isOrigin()
    {
        return origin;
    }

    public void setOrigin(Boolean origin)
    {
        this.origin = origin;
    }

    public Long getMessageId()
    {
        return messageId;
    }

    public void setMessageId(Long messageId)
    {
        this.messageId = messageId;
    }


    public MessageIntern()
    {
    }

    public MessageIntern(long id, Date date, User user, String topic, String content, Boolean isOrigin)
    {
        this(id, date, user, topic, content, isOrigin, -1);
    }

    public MessageIntern(long id, Date date, User user, String topic, String content, Boolean isOrigin, long originId)
    {
        this.messageId = id;
        this.originId = originId;
        
        setDate(date);
        setUser(user);
        setTopic(topic);
        setContent(content);
        setOrigin(isOrigin);
    }

    @Override
    public int hashCode()
    {
        final int prime = 89;
        int result = 1;
        result = prime * result;
        if(messageId == null)
        {
            result +=  messageId.hashCode();
        }
        return result;
    }

    @Override
    public boolean equals(Object obj)
    {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;
        
        Message m = (Message) obj;
        if (messageId == null && m.getMessageId() != null)
        {
            return false;
            
        } else if (messageId.equals(m.getMessageId()) == false)
        {
            return false;
        }
        return true;
    }
}
