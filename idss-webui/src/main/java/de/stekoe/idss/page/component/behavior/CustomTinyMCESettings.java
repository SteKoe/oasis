package de.stekoe.idss.page.component.behavior;

import de.stekoe.idss.theme.BootstrapTheme;
import wicket.contrib.tinymce.settings.Button;
import wicket.contrib.tinymce.settings.TinyMCESettings;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
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
        settings.setContentCss(BootstrapTheme.CSS_TINYMCE);
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
