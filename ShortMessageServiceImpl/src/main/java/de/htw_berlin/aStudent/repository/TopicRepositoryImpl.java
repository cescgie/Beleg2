package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.TopicIntern;
import org.springframework.stereotype.Repository;
import javax.persistence.Query;
import java.math.BigInteger;



@Repository
public class TopicRepositoryImpl extends AbstractRepository<TopicIntern> implements TopicRepository
{

    public TopicRepositoryImpl()    {
        setClazz(TopicIntern.class);
    }

    @Override
    public Integer getAvailableTopicCount() {
        Query q = entityManager.createNativeQuery("SELECT COUNT(*) FROM topicintern");
        BigInteger i = (BigInteger) q.getSingleResult();
        return i.intValue();
    }

    @Override
    public void deleteAllTopics() {
        Query q = entityManager.createNativeQuery("DELETE FROM topicintern");
        q.executeUpdate();
    }
}
