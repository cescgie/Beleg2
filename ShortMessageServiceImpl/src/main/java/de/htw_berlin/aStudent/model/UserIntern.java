package de.htw_berlin.aStudent.model;

import de.htw_berlin.f4.ai.kbe.kurznachrichten.User;
import javax.persistence.*;
import java.io.Serializable;

@Entity
@Table (name = "userintern")
public class UserIntern extends User implements Serializable
{
    @Column
    private String name = "";
    
    @Column
    private String city = "";
    
    @Id
    @GeneratedValue (strategy = GenerationType.AUTO)
    private Long id = 0l;

    public String getName() 
    {
        return name;
    }
    public void setName(String name) 
    {
        this.name = name;
    }
    public String getCity() 
    {
        return city;
    }
    public void setCity(String city) 
    {
        this.city = city;
    }

    public UserIntern()
    {
        // todo???
    }

    public UserIntern(String name, String city)
    {
        setName(name);
        setCity(city);
    }

    public long getId()
    {
        return id;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
        {
            return true;
        }
        if (obj == null || getClass() != obj.getClass())
        {
            return false;
        }
        
        User other = (User) obj;
        
        if (name == null && other.getName() != null) 
        {
            return false;
        } else if (name.equals(other.getName()) == false)
        {
            return false;
        }
        
        return true;
    }

}
