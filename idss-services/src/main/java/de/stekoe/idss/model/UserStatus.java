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

package de.stekoe.idss.model;

/**
 * In order to add more semantic to a users status, this enumeration may be used to
 * set a user's status.
 *
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public enum UserStatus {
    /**
     * Just for test users. Users having this status will not be able to
     * authenticate in production mode!
     */
    TEST,
    /**
     * All registered users will have this status until they have activated their account.
     */
    ACTIVATION_PENDING,
    /**
     * Registered users who have activated their account.
     */
    ACTIVATED,
    /**
     * Admins are able to deactivate user accounts.
     */
    DEACTIVATED,
    /**
     * If a user wants the admin not just to disable the account, this status marks the user as
     * deleted.
     */
    DELETED,
    /**
     * User has requested a new password.
     */
    RESET_PASSWORD;
}
