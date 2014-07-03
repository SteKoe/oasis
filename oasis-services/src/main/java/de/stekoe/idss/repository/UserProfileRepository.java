package de.stekoe.idss.repository;

import org.springframework.data.repository.CrudRepository;

import de.stekoe.idss.model.UserProfile;

public interface UserProfileRepository extends CrudRepository<UserProfile, String> {
}
