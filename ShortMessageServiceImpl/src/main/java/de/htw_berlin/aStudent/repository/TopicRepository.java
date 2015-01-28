package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.TopicIntern;

import java.util.List;

public interface TopicRepository
{

    public TopicIntern findOne(Long id);

    public List<TopicIntern> findAll();

    public void save(TopicIntern entity);

    public TopicIntern update(TopicIntern entity);

    public void delete(TopicIntern entity);

    public void deleteById(Long entityId);

    public Integer getAvailableTopicCount();

    public void deleteAllTopics();
}