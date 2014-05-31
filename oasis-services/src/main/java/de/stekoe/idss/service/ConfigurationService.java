package de.stekoe.idss.service;

import java.util.List;

import javax.inject.Inject;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;
import org.springframework.stereotype.Service;

@Service
public class ConfigurationService {
    private static final Logger LOG = Logger.getLogger(ConfigurationService.class);

    @Inject @Qualifier("propertyPlaceholderConfigurer")
    PropertyPlaceholderConfigurer configurer;

    public List<PropertyGroup> getConfiguration() {
        return null;
    }
}
