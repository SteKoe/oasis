package de.stekoe.idss;

import java.net.URL;

import org.apache.log4j.Logger;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import de.stekoe.idss.service.IUserService;

/**
 * @author Stephan Köninger <mail@stekoe.de>
 */
public class IDSSServices {

    private static final Logger LOG = Logger.getLogger(IDSSServices.class);

    private static ClassPathXmlApplicationContext context;

    /**
     * @return The UserService Bean
     */
    public static IUserService getUserService() {
        return getBeanInternal("userService");
    }

    // ################# Utilities              ´
    @SuppressWarnings("unused")
    private static final <T> T getBeanInternal(final String beanName) {
        if (context == null) {
            context = new ClassPathXmlApplicationContext(getApplicationContextUrl());
        }
        return (T) context.getBean(beanName);
    }

    private static final String getApplicationContextUrl() {
        URL resource = IDSSServices.class.getResource("/spring/BeanLocations.xml");
        LOG.info("Loading bean definitions from: " + resource);
        return resource.toString();
    }
}
