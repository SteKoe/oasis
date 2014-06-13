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

package de.stekoe.idss.page.company;

import org.apache.wicket.model.Model;
import org.apache.wicket.spring.injection.annot.SpringBean;

import de.stekoe.idss.model.User;
import de.stekoe.idss.service.UserService;

public abstract class AddEmployeeModal extends AddOrInviteUserModal {

    @SpringBean
    private UserService userService;

    private String user;

    public AddEmployeeModal(String id) {
        super(id);

        header(Model.of(getString("label.project.add.member")));
    }

    public abstract void onSave(User existingUser);
}
