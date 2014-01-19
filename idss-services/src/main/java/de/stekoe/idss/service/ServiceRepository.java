package de.stekoe.idss.service;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.net.URL;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public class ServiceRepository {

    private static final Logger LOG = Logger.getLogger(ServiceRepository.class);

    private static ClassPathXmlApplicationContext context;

    /**
     * @return The UserService Bean
     */
//    public static UserService getUserService() {
//        return getBeanInternal("userService");
//    }

//    public static AuthService getAuthService() {
//        return getBeanInternal("authService");
//    }

    // ################# Utilities              ´
    @SuppressWarnings("unused")
    private static final <T> T getBeanInternal(final String beanName) {
        if (context == null) {
            context = new ClassPathXmlApplicationContext(getApplicationContextUrl());
        }
        return (T) context.getBean(beanName);
    }

    private static final String getApplicationContextUrl() {
        URL resource = ServiceRepository.class.getResource("/services/BeanLocations.xml");
        LOG.info("Loading bean definitions from: " + resource);
        return resource.toString();
    }
}
