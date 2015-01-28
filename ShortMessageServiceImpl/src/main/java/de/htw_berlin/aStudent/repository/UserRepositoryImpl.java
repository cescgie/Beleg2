package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.UserIntern;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import java.math.BigInteger;

@Repository
public class UserRepositoryImpl extends AbstractRepository<UserIntern> implements UserRepository
{

    public UserRepositoryImpl()    {
        setClazz(UserIntern.class);
    }

    
    @Override
    public Integer getAvailableUserCount() {
        Query q = entityManager.createNativeQuery("SELECT COUNT(*) FROM userintern");
        BigInteger i = (BigInteger) q.getSingleResult();
        return i.intValue();
    }
    

    @Override
    public void deleteAllUsers() {
        Query q = entityManager.createNativeQuery("DELETE FROM userintern");
        q.executeUpdate();
    }
}
