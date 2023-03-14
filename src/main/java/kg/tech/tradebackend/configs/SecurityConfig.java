package kg.tech.tradebackend.configs;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.io.IOException;
import java.util.Set;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    @Value("${spring.default.redirect-url}")
    private String defaultRedirect;

    public SecurityConfig(@Qualifier("userServiceImpl")UserDetailsService userDetailsService,
                          PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable();

        http
                .authorizeRequests()
                .antMatchers("/").permitAll()
                .antMatchers("/ext-api/**").permitAll()
                .antMatchers("/admin/**").access("hasAnyRole('ROLE_ADMIN')")
                .antMatchers("/api/**").access("hasAnyRole('ROLE_USER','ROLE_ADMIN')")
                .and()
                .formLogin()
                .successHandler((request, response, authentication) -> {
                    Set<String> roles = AuthorityUtils.authorityListToSet(authentication.getAuthorities());

                    if (roles.contains("ROLE_ADMIN")) {
                        response.sendRedirect(defaultRedirect + "/admin-panel/Dashboard");
                    } else if (roles.contains("ROLE_USER")) {
                        response.sendRedirect(defaultRedirect + "/");
                    }
                })
                .and()
                .logout()
                .addLogoutHandler((request, response, authentication) -> {
                    try {
                        response.sendRedirect(defaultRedirect + "/");
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
    }


}
