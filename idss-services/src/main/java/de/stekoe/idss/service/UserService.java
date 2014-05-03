/*
 * Copyright 2014 Stephan Koeninger
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * 	http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package de.stekoe.idss.service;

import java.util.List;

import javax.inject.Inject;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import de.stekoe.idss.model.User;
import de.stekoe.idss.model.UserStatus;
import de.stekoe.idss.repository.UserRepository;

@Service
@Transactional(readOnly = true)
public class UserService {

    @Inject
    private UserRepository userRepository;

    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public User findByUsernameOrEmail(String value) {
        return userRepository.findByUsernameOrEmail(value);
    }

    public User findOne(String id) {
        return userRepository.findOne(id);
    }

    public long count() {
        return userRepository.count();
    }

    @Transactional
    public void save(User entity) throws UserException {
        if (emailInUse(entity)) {
            throw new EmailAddressAlreadyInUseException();
        } else if (usernameInUse(entity)) {
            throw new UsernameAlreadyInUseException();
        } else {
            userRepository.save(entity);
        }
    }

    @Transactional
    public void delete(User entity) {
        userRepository.delete(entity);
    }

    private boolean usernameInUse(User user) {
        final User userFromDB = userRepository.findByUsername(user.getUsername());
        if (userFromDB == null) {
            return false;
        } else if (userFromDB.getId().equals(user.getId())) {
            return false;
        }

        return true;
    }

    private boolean emailInUse(User user) {
        final User userFromDB = userRepository.findByEmail(user.getEmail());
        if (userFromDB == null) {
            return false;
        } else if (userFromDB.getId().equals(user.getId())) {
            return false;
        }

        return true;
    }

    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    public User findByActivationCode(String code) {
        return userRepository.findByActivationCode(code);
    }

    public List<String> findAllUsernames() {
        return userRepository.findAllUsernames();
    }

    public List<String> findAllEmailAddresses() {
        return userRepository.findAllEmailAddresses();
    }

    public List<User> findAll() {
        return (List<User>) userRepository.findAll();
    }

    public User findByPasswordResetToken(String passwordResetToken) {
        return userRepository.findByPasswordResetToken(passwordResetToken, UserStatus.RESET_PASSWORD);
    }
}
