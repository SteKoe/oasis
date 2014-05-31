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

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class UserTest {

    private User user;

    @Before
    public void setUp() throws Exception {
        user = new User();
    }

    @Test
    public void testEqualsOnSameObject() throws Exception {
        assertTrue(user.equals(user));
    }

    @Test
    public void testEqualsWithNull() throws Exception {
        assertFalse(user.equals(null));
    }

    @Test
    public void testEqualsWithDifferentObject() throws Exception {
        final UserProfile userProfile = new UserProfile();
        assertFalse(user.equals(userProfile));
    }

    @Test
    public void testHasAnyRoleWithNull() throws Exception {
        user.hasAnyRole(null);
    }

    @Test
    public void testHasAnyRoleWithEmtpyList() throws Exception {
        user.hasAnyRole(new ArrayList<SystemRole>());
    }
}
