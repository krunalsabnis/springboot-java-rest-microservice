package com.kru.qualibrate;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * @author <a href="mailto:krunalsabnis@gmail.com">Krunal Sabnis</a>
 *
 */

@Configuration
@EnableWebSecurity
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {
    public static final String ADMIN_ROLE = "ADMIN";

    @Value("${secure.admin.password}")
    private String adminPassword;

    public SecurityConfiguration() {
        super(true);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.exceptionHandling().and()

                // Allow anonymous access
                .anonymous().and()

                // Enable Basic Authentication
                .httpBasic().realmName("Qualibrate API 2.0").and()

                .servletApi().and()

                // Stateless session management
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and()

                // Disable CSRF & Frame Options since we're not serving HTML content
                .csrf().disable()
                .headers().frameOptions().disable().and()
                .headers().xssProtection().disable().and()


                .authorizeRequests()
                // Allow anonymous resource requests
                .antMatchers("/").permitAll()
                // yet to enable beanstalk health  and info url
                .antMatchers("/health").permitAll()
                .antMatchers("/info").permitAll()
                .antMatchers("/favicon.ico").permitAll()
                // Secure all APIs
                .regexMatchers("/api/v[0-9]+/.*").hasAnyRole(ADMIN_ROLE);

    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication().passwordEncoder(new BCryptPasswordEncoder())
                .withUser("admin").password(adminPassword).roles(ADMIN_ROLE);
    }
}
