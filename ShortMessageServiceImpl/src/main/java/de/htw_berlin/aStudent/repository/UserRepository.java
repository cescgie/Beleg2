package de.htw_berlin.aStudent.repository;

import de.htw_berlin.aStudent.model.UserIntern;
import java.util.List;

public interface UserRepository
{

    public UserIntern findOne(Long id);

    public List<UserIntern> findAll();

    public void save(UserIntern entity);

    public UserIntern update(UserIntern entity);

    public void delete(UserIntern entity);

    public void deleteById(Long entityId);

    public Integer getAvailableUserCount();

    public void deleteAllUsers();
}