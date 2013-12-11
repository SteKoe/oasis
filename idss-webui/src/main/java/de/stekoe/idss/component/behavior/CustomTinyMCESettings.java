package de.stekoe.idss.component.behavior;

import org.apache.wicket.request.Url;
import org.apache.wicket.request.resource.UrlResourceReference;
import wicket.contrib.tinymce.settings.TinyMCESettings;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class CustomTinyMCESettings {
    public static TinyMCESettings getStandard() {
        TinyMCESettings settings = new TinyMCESettings();
        settings.setToolbarLocation(TinyMCESettings.Location.top);
        settings.setToolbarAlign(TinyMCESettings.Align.left);
        settings.setContentCss(new UrlResourceReference(Url.parse("/css/tinymce.css")));

        return settings;
    }
}
