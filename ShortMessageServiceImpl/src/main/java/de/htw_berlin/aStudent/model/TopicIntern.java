package de.htw_berlin.aStudent.model;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name ="topicintern")
public class TopicIntern implements Serializable
{
    
    
    @Column
    private String topic = "";
    
    @Column
    private String userName = "";
    
    @Id
    @GeneratedValue
    private long id = 0;

    public String getUserName()
    {
        return userName;
    }

    public String getTopic()
    {
        return topic;
    }

    public long getId()
    {
        return id;
    }

    public TopicIntern()
    {
    }

    public TopicIntern(String userName, String topic)
    {
        this.topic = topic;
        this.userName = userName;
    }
}
