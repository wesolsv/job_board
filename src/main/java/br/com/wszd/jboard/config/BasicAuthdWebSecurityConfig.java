package br.com.wszd.jboard.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class BasicAuthdWebSecurityConfig {

    @Autowired
    private AppBasicAuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    protected SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/api/v1/person/login").permitAll()
                .antMatchers("/api/v1/person").hasAnyRole("USER")
                .antMatchers(HttpMethod.POST,"/api/v1/company/").hasAnyRole("COMP")
                .anyRequest().permitAll()
                .and().httpBasic()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS);
        return http.build();
    }

    @Bean
    public InMemoryUserDetailsManager userDetailsManager(){
        UserDetails user = User.withUsername("user")
                .password("$2a$08$YepEcJpKLoUZzTuI1iqdlejcS9nQXgw0wmCpCOUonKYH.E0BHL.SK")
                .roles("USER")
                .build();

        UserDetails comp = User.withUsername("comp")
                .password("$2a$08$YepEcJpKLoUZzTuI1iqdlejcS9nQXgw0wmCpCOUonKYH.E0BHL.SK")
                .roles("COMP")
                .build();

        UserDetails admin = User.withUsername("admin")
                .password("$2a$08$YepEcJpKLoUZzTuI1iqdlejcS9nQXgw0wmCpCOUonKYH.E0BHL.SK")
                .roles("ADMIN")
                .build();

        return new InMemoryUserDetailsManager(user, comp, admin);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(8);
    }
}
