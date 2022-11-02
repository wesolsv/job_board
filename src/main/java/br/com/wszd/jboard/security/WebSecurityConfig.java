package br.com.wszd.jboard.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class WebSecurityConfig {

    private static final String[] SWAGGER_WHITELIST = {
            "/v2/api-docs",
            "/swagger-resources",
            "/swagger-resources/**",
            "/configuration/ui",
            "/configuration/security",
            "/swagger-ui.html",
            "/webjars/**"
    };

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.csrf().disable()
                .authorizeRequests()
                .antMatchers(SWAGGER_WHITELIST).permitAll()
                .antMatchers(HttpMethod.POST,"/api/v1/person").anonymous()
                .antMatchers(HttpMethod.POST,"/api/v1/company").anonymous()
                .antMatchers(HttpMethod.POST,"/api/v1/users/login").anonymous()
                .antMatchers(HttpMethod.POST,"/api/v1/person/candidacy").hasAnyRole( "USER", "ADMIN")
                .antMatchers(HttpMethod.GET,"/api/v1/person/candidacy/{id}").hasAnyRole( "USER", "ADMIN")
                .antMatchers(HttpMethod.PUT,"/api/v1/person/{id}").hasAnyRole( "USER", "ADMIN")
                .antMatchers(HttpMethod.GET,"/api/v1/person/{id}").hasAnyRole( "USER", "ADMIN")
                .antMatchers(HttpMethod.PUT,"/api/v1/company/{id}").hasAnyRole("COMP", "ADMIN")
                .antMatchers(HttpMethod.GET,"/api/v1/company/{id}").hasAnyRole("COMP", "ADMIN")
                .antMatchers(HttpMethod.GET,"/api/v1/company/candidate/{id}").hasAnyRole("COMP", "ADMIN")
                .antMatchers(HttpMethod.POST,"/api/v1/company/candidate/hire").hasAnyRole("COMP", "ADMIN")
                .antMatchers("/api/v1/job/**").hasAnyRole("COMP", "ADMIN")
                .anyRequest().hasAnyRole("ADMIN")
                .and().sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().addFilterBefore(new JWTFilter(), UsernamePasswordAuthenticationFilter.class).build();
    }
}
