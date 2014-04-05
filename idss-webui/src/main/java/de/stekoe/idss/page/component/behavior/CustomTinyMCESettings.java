/*
 * Copyright 2014 Stephan Köninger
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

package de.stekoe.idss.page.component.behavior;

import java.util.ArrayList;
import java.util.List;

import wicket.contrib.tinymce.settings.Button;
import wicket.contrib.tinymce.settings.TinyMCESettings;

public class CustomTinyMCESettings {

    private CustomTinyMCESettings() {
        // NOP
    }

    public static TinyMCESettings getStandard() {
        TinyMCESettings settings = new TinyMCESettings(TinyMCESettings.Theme.advanced);
        settings.setToolbarButtons(TinyMCESettings.Toolbar.first, getButtonsForFirstToolbar());
        settings.setToolbarLocation(TinyMCESettings.Location.top);
        settings.setToolbarAlign(TinyMCESettings.Align.center);
        settings.setStatusbarLocation(null);
        settings.addCustomSetting("width: '100%'");
        settings.addCustomSetting("height: '200px'");

        return settings;
    }

    private static List<Button> getButtonsForFirstToolbar() {
        List<Button> buttons = new ArrayList<Button>();
        buttons.add(Button.undo);
        buttons.add(Button.redo);
        buttons.add(Button.separator);

        buttons.add(Button.formatselect);
        buttons.add(Button.separator);

        buttons.add(Button.bold);
        buttons.add(Button.italic);
        buttons.add(Button.underline);
        buttons.add(Button.strikethrough);
        buttons.add(Button.separator);

        buttons.add(Button.justifyleft);
        buttons.add(Button.justifycenter);
        buttons.add(Button.justifyright);
        buttons.add(Button.justifyfull);
        buttons.add(Button.separator);

        buttons.add(Button.bullist);
        buttons.add(Button.numlist);
        buttons.add(Button.separator);

        buttons.add(Button.link);
        buttons.add(Button.image);

        return buttons;
    }
}
