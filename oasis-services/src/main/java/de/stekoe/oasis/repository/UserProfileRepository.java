package de.stekoe.oasis.repository;

import org.springframework.data.repository.CrudRepository;

import de.stekoe.oasis.model.UserProfile;

public interface UserProfileRepository extends CrudRepository<UserProfile, String> {
}
