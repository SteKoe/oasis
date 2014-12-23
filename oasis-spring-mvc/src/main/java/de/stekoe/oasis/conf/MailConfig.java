package de.stekoe.oasis.conf;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
public class MailConfig  {
    @Value("${mail.host}")
    private String host;

    @Value("${mail.port}")
    private String port;

    @Value("${mail.auth:false}")
    private String auth;

    @Value("${mail.ssl:false}")
    private String ssl ;

    @Value("${mail.user:}")
    private String user;

    @Value("${mail.password:}")
    private String password;

    @Value("${mail.protocol:smtp}")
    private String protocol;

    @Value("${mail.debug:false}")
    private String debug;

    @Bean
    public JavaMailSender javaMailService() {
        JavaMailSenderImpl javaMailSender = new JavaMailSenderImpl();

        javaMailSender.setHost(host);
        javaMailSender.setPort(Integer.parseInt(port));
        javaMailSender.setProtocol(protocol);

        if(Boolean.valueOf(auth)) {
            javaMailSender.setUsername(user);
            javaMailSender.setPassword(password);
        }

        javaMailSender.setJavaMailProperties(getMailProperties());

        return javaMailSender;
    }

    private Properties getMailProperties() {
        Properties properties = new Properties();
        properties.setProperty("mail.smtp.localhost", host);
        properties.setProperty("mail.smtp.port", port);
        properties.setProperty("mail.smtp.auth", String.valueOf(auth));
        properties.setProperty("mail.smtp.starttls.enable", String.valueOf(ssl));
        properties.setProperty("mail.transport.protocol", protocol);
        properties.setProperty("mail.debug", String.valueOf(debug));
        return properties;
    }

    public String getHost() {
        return host;
    }

    public String getPort() {
        return port;
    }

    public String getAuth() {
        return auth;
    }

    public String getSsl() {
        return ssl;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public String getProtocol() {
        return protocol;
    }

    public String getDebug() {
        return debug;
    }
}



