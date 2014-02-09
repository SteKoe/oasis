package de.stekoe.idss.component.behavior;

import de.stekoe.idss.theme.BootstrapTheme;
import wicket.contrib.tinymce.settings.TinyMCESettings;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class CustomTinyMCESettings {
    public static TinyMCESettings getStandard() {
        TinyMCESettings settings = new TinyMCESettings();
        settings.setToolbarLocation(TinyMCESettings.Location.top);
        settings.setToolbarAlign(TinyMCESettings.Align.left);
        settings.setContentCss(BootstrapTheme.CSS_TINYMCE);
        settings.addCustomSetting("width: '100%'");
        settings.addCustomSetting("height: '200px'");

        return settings;
    }
}
