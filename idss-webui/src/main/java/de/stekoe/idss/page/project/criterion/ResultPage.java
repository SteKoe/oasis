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

package de.stekoe.idss.page.project.criterion;

import de.stekoe.idss.page.project.ProjectPage;
import de.stekoe.idss.theme.BootstrapTheme;
import org.apache.wicket.markup.head.IHeaderResponse;
import org.apache.wicket.markup.head.JavaScriptReferenceHeaderItem;
import org.apache.wicket.request.mapper.parameter.PageParameters;
import org.apache.wicket.request.resource.CssResourceReference;
import org.apache.wicket.request.resource.JavaScriptResourceReference;

public class ResultPage extends ProjectPage {

    public static final CssResourceReference CSS_BOOTSTRAP = new CssResourceReference(BootstrapTheme.class, "vendors/amcharts/amcharts/css/bootstrap.min.css");
    public static final JavaScriptResourceReference AMCHARTS = new JavaScriptResourceReference(BootstrapTheme.class, "vendors/amcharts/amcharts/amcharts.js");
    public static final JavaScriptResourceReference RADAR = new JavaScriptResourceReference(BootstrapTheme.class, "vendors/amcharts/amcharts/radar.js");
    public static final JavaScriptResourceReference CHALK = new JavaScriptResourceReference(BootstrapTheme.class, "vendors/amcharts/amcharts/themes/chalk.js");

    public ResultPage(PageParameters pageParameters) {
        super(pageParameters);
    }

    @Override
    public void renderHead(IHeaderResponse response) {
        super.renderHead(response);
        response.render(JavaScriptReferenceHeaderItem.forReference(AMCHARTS));
        response.render(JavaScriptReferenceHeaderItem.forReference(RADAR));
        response.render(JavaScriptReferenceHeaderItem.forReference(CHALK));
    }
}
