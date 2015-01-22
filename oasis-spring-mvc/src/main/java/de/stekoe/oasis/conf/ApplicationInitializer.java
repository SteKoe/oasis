package de.stekoe.oasis.conf;

import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.support.OpenEntityManagerInViewFilter;
import org.springframework.web.WebApplicationInitializer;
import org.springframework.web.context.ContextLoaderListener;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.multipart.support.MultipartFilter;
import org.springframework.web.servlet.DispatcherServlet;

import javax.servlet.FilterRegistration;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration;

@Configuration
public class ApplicationInitializer implements WebApplicationInitializer {
    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        AnnotationConfigWebApplicationContext rootContext = getContext();
        servletContext.addListener(new ContextLoaderListener(rootContext));
        rootContext.setServletContext(servletContext);

        ServletRegistration.Dynamic dispatcher = servletContext.addServlet("dispatcher", new DispatcherServlet(rootContext));
        dispatcher.setLoadOnStartup(1);
        dispatcher.addMapping("/*");

        FilterRegistration.Dynamic encodingFilter = servletContext.addFilter("encodingFilter", getCharacterEncodingFilter());
        encodingFilter.addMappingForUrlPatterns(null, false, "/*");

        // MultipartFilter BEFORE SpringSecurityFilter as CSRF won't work for uploads otherwise!
        FilterRegistration.Dynamic multipartFilter = servletContext.addFilter("multipartFilter", new MultipartFilter());
        multipartFilter.addMappingForUrlPatterns(null, false, "/*");

        FilterRegistration.Dynamic securityFilter = servletContext.addFilter("securityFilter", new DelegatingFilterProxy("springSecurityFilterChain"));
        securityFilter.addMappingForUrlPatterns(null, false, "/*");

        FilterRegistration.Dynamic hibernateFilter = servletContext.addFilter("hibernateFilter", new OpenEntityManagerInViewFilter());
        hibernateFilter.addMappingForUrlPatterns(null, false, "/*");
    }

    private CharacterEncodingFilter getCharacterEncodingFilter() {
        CharacterEncodingFilter filter = new CharacterEncodingFilter();
        filter.setEncoding("UTF-8");
        filter.setForceEncoding(true);
        return filter;
    }

    private AnnotationConfigWebApplicationContext getContext() {
        // Load application context
        AnnotationConfigWebApplicationContext rootContext = new AnnotationConfigWebApplicationContext();

        // Important! Scan package for config files and do not assign single ApplicationContext.class!
        rootContext.setConfigLocation("de.stekoe.oasis.conf");
        return rootContext;
    }
}