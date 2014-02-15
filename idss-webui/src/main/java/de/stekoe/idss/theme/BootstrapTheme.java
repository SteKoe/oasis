package de.stekoe.idss.theme;

import de.agilecoders.wicket.core.settings.Theme;
import org.apache.wicket.markup.head.CssReferenceHeaderItem;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;
import org.apache.wicket.request.resource.PackageResourceReference;

/**
 * @author Stephan Koeninger <mail@stephan-koeninger.de>
 */
public class BootstrapTheme extends Theme {

    public static final String NAME = "OASISBootstrapTheme";

    public static final CssResourceReference CSS_BOOTSTRAP = new CssResourceReference(BootstrapTheme.class, "vendors/bootstrap/css/bootstrap.min.css");
    public static final CssResourceReference CSS_BOOTSTRAP_THEME = new CssResourceReference(BootstrapTheme.class, "vendors/bootstrap/css/bootstrap-theme.min.css");
    public static final CssResourceReference CSS_JQUERY_UI = new CssResourceReference(BootstrapTheme.class, "vendors/jquery-ui/css/theme/jquery-ui-1.10.3.custom.css");
    public static final CssResourceReference CSS_FONT_AWESOME = new CssResourceReference(BootstrapTheme.class, "vendors/font-awesome/css/font-awesome.min.css");
    public static final CssResourceReference CSS_CUSTOM = new CssResourceReference(BootstrapTheme.class, "css/custom.css");
    public static final CssResourceReference CSS_TINYMCE = new CssResourceReference(BootstrapTheme.class, "css/tinymce.css");

    public static final JavaScriptResourceReference JS_JQUERY = new JavaScriptResourceReference(BootstrapTheme.class, "vendors/jquery/jquery.min.js");
    public static final JavaScriptResourceReference JS_JQUERY_UI = new JavaScriptResourceReference(BootstrapTheme.class, "vendors/jquery-ui/js/jquery-ui-1.10.3.custom.js");
    public static final JavaScriptResourceReference JS_BOOTSTRAP = new JavaScriptResourceReference(BootstrapTheme.class, "vendors/bootstrap/js/bootstrap.min.js");
    public static final JavaScriptResourceReference JS_CUSTOM = new JavaScriptResourceReference(BootstrapTheme.class, "js/custom.js");

    public BootstrapTheme() {
        super(NAME, new PackageResourceReference(BootstrapTheme.class, "/webapp/vendors/bootstrap/css/bootstrap.css"));
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);

        // jQuery =====
        response.render(JavaScriptReferenceHeaderItem.forReference(BootstrapTheme.JS_JQUERY));

        // jQuery UI =====
        //response.render(CssReferenceHeaderItem.forReference(BootstrapTheme.CSS_JQUERY_UI));
        //response.render(JavaScriptReferenceHeaderItem.forReference(BootstrapTheme.JS_JQUERY_UI));

        // Bootstrap =====
        response.render(CssReferenceHeaderItem.forReference(BootstrapTheme.CSS_BOOTSTRAP));
        //response.render(CssReferenceHeaderItem.forReference(BootstrapTheme.CSS_BOOTSTRAP_THEME));
        response.render(JavaScriptReferenceHeaderItem.forReference(BootstrapTheme.JS_BOOTSTRAP));

        // Font Awesome =====
        response.render(CssReferenceHeaderItem.forReference(BootstrapTheme.CSS_FONT_AWESOME));

        // Custom CSS / JS =====
        response.render(CssReferenceHeaderItem.forReference(BootstrapTheme.CSS_CUSTOM));
        response.render(JavaScriptReferenceHeaderItem.forReference(BootstrapTheme.JS_CUSTOM));
    }
}
