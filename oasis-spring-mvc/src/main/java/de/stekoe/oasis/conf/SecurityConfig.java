package de.stekoe.oasis.conf;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.sql.DataSource;

@Configuration
@EnableWebSecurity
@EnableWebMvcSecurity
@EnableGlobalMethodSecurity(prePostEnabled=true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    DataSource dataSource;

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.jdbcAuthentication()
                .dataSource(dataSource)
                .usersByUsernameQuery(getUserQuery())
                .authoritiesByUsernameQuery(getAuthoritiesQuery())
                .passwordEncoder(new BCryptPasswordEncoder());
    }

    private String getUserQuery() {
        return "SELECT username, password, userStatus = 'ACTIVATED' as enabled " +
                    "FROM User " +
                    "WHERE email = ?";
    }

    private String getAuthoritiesQuery() {
        return "SELECT username, SystemRole.name " +
                    "FROM User " +
                    "LEFT JOIN User_SystemRole USING(user_id) " +
                    "LEFT JOIN SystemRole USING(system_role_id) " +
                    "WHERE email = ?";
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                    .antMatchers("/user/**").access("hasRole('USER')")
                    .antMatchers("/project/**").access("hasRole('USER')")
                .and()
                    .formLogin().loginPage("/login")
                    .failureUrl("/login?error")
                    .defaultSuccessUrl("/project")
                    .usernameParameter("email")
                    .passwordParameter("password")
                .and()
                    .logout()
                    .logoutUrl("/logout")
                    .logoutSuccessUrl("/login?logout")
                .and()
                    .csrf()
                .and()
                    .exceptionHandling()
                    .accessDeniedPage("/403");
    }
}
