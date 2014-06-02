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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.junit.Assert.assertThat;

import org.junit.Before;
import org.junit.Test;

public class SystemRoleTest {
    private SystemRole systemRole;

    @Before
    public void setUp() throws Exception {
        systemRole = new SystemRole();
        systemRole.setName("ABC");
    }

    @Test
    public void testEquals() throws Exception {
        SystemRole role1 = new SystemRole("ADMIN");
        SystemRole role2 = new SystemRole("ADMIN");
        assertThat(role1.equals(role1), equalTo(true));
        assertThat(role1.equals(role2), equalTo(true));
    }

    @Test
    public void testHashCode() throws Exception {

    }
}
