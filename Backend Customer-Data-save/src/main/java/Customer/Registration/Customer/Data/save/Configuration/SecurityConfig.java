package Customer.Registration.Customer.Data.save.Configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.client.RestTemplate;


@Configuration
@EnableMethodSecurity
@EnableWebSecurity
public class SecurityConfig {



    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        httpSecurity.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(authz -> authz
                        .requestMatchers("/authenticate", "/register","/swagger-ui.html","/v3/api-docs/**","/swagger-ui/**","/customer/**").permitAll()
                        .anyRequest().authenticated()
                );

        return httpSecurity.build();
    }


    @Bean
    public UserDetailsService userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user")
                .password("f84db008-e160-4cb3-8490-91001d2bd823")
                .roles("USER")
                .roles("Admin")
                .roles("admin")
                .roles("user")
                .build();

        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}