package de.stekoe.idss.service;

import de.stekoe.idss.model.Project;
import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserChoice;
import de.stekoe.idss.model.UserChoices;
import de.stekoe.idss.repository.UserChoicesRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.inject.Inject;
import java.util.List;

@Service
@Transactional(readOnly = true)
public class UserChoicesService {

    @Inject
    private UserChoicesRepository userChoicesRepository;

    public UserChoices findByUserAndProject(User user, Project project) {
        return userChoicesRepository.findByUserAndProject(user, project);
    }

    @Transactional
    public void delete(Iterable<UserChoices> entities) {
        userChoicesRepository.delete(entities);
    }

    @Transactional
    public void delete(String s) {
        userChoicesRepository.delete(s);
    }

    public boolean exists(String s) {
        return userChoicesRepository.exists(s);
    }

    @Transactional
    public void delete(UserChoices entity) {
        userChoicesRepository.delete(entity);
    }

    public UserChoices findOne(String s) {
        return userChoicesRepository.findOne(s);
    }

    @Transactional
    public void deleteAll() {
        userChoicesRepository.deleteAll();
    }

    public long count() {
        return userChoicesRepository.count();
    }

    public Iterable<UserChoices> findAll(Iterable<String> strings) {
        return userChoicesRepository.findAll(strings);
    }

    public Iterable<UserChoices> findAll() {
        return userChoicesRepository.findAll();
    }

    @Transactional
    public UserChoices save(UserChoices entity) {
        return userChoicesRepository.save(entity);
    }

    @Transactional
    public Iterable<UserChoices> save(Iterable<UserChoices> entities) {
        return userChoicesRepository.save(entities);
    }

    public List<UserChoices> findByProject(String projectId) {
        return userChoicesRepository.findByProject(projectId);
    }
}
