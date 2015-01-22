package de.stekoe.oasis.repository;

import de.stekoe.oasis.model.User;
import de.stekoe.oasis.model.UserStatus;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface UserRepository extends CrudRepository<User, String> {
    @Query("SELECT u FROM User u WHERE u.username = ?1")
    User findByUsername(String username);

    @Query("SELECT u FROM User u WHERE u.activationKey = ?1")
    User findByActivationCode(String code);

    @Query("SELECT u FROM User u WHERE u.email = ?1")
    User findByEmail(String email);

    @Query("SELECT u FROM User u WHERE u.username = ?1 OR u.email = ?1")
    User findByUsernameOrEmail(String value);

    @Query("SELECT u.username FROM User u")
    List<String> findAllUsernames();

    @Query("SELECT u.email FROM User u")
    List<String> findAllEmailAddresses();

    @Query("SELECT u FROM User u WHERE u.activationKey = ?1 AND u.userStatus = ?2")
    User findByPasswordResetToken(String passwordResetToken, UserStatus userStatus);
}
