package com.airline.ticketbookingapi.config;

import com.airline.ticketbookingapi.service.impl.UserDetailsServiceImpl;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Clase de configuracin principal de Spring Security para la API de reserva de tiquetes.
 * Define las reglas de autorizacin, el mtodo de autenticacin (Basic Auth) y el codificador de contraseas.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {


    /**
     * Define la cadena de filtros de seguridad HTTP, incluyendo la gestin de CSRF, autenticacin,
     * poltica de sesin y reglas de autorizacin por URL.
     *
     * @param httpSecurity Objeto HttpSecurity para configurar la seguridad a nivel HTTP.
     * @return SecurityFilterChain configurada.
     * @throws Exception Si ocurre un error durante la configuracin.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception {
        return httpSecurity
                .csrf(csrf -> csrf.disable())
                .httpBasic(Customizer.withDefaults())
                .sessionManagement(session -> session
                        .sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(http-> {

                    /*---------------- Configuración de endpoints PÚBLICOS ----------------*/
                    http.requestMatchers(HttpMethod.GET, "/api/v1/airline/auth/test").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/v1/airline/auth/login").permitAll();
                    http.requestMatchers("/api/v1/airline/swagger-ui/**").permitAll();
                    http.requestMatchers(HttpMethod.POST, "/api/v1/airline/users/create").permitAll();

                    // Permiso para buscar vuelos es pblico/INVITED
                    http.requestMatchers(HttpMethod.GET, "/api/v1/airline/flights/search").hasAnyAuthority("READ", "INVITED");

                    /*---------------- Configuración de endpoints PRIVADOS (por Permiso o Rol) ----------------*/

                    // Endpoint de prueba asegurado
                    http.requestMatchers(HttpMethod.GET, "/auth/test-secured").hasAuthority("READ");

                    // Endpoints de Users
                    http.requestMatchers(HttpMethod.PUT, "/users/update/{id}").hasAnyRole("ADMIN", "DEVELOPER");
                    http.requestMatchers(HttpMethod.GET, "/find/{id}").hasAnyRole("ADMIN", "DEVELOPER", "USER", "INVITED");
                    http.requestMatchers(HttpMethod.DELETE, "/users/delete/{id}").hasAnyRole("ADMIN", "DEVELOPER");
                    http.requestMatchers(HttpMethod.GET, "/users/findAll").hasAnyRole("ADMIN", "DEVELOPER");

                    // Endpoints de Reservations
                    http.requestMatchers(HttpMethod.POST, "/reservations/create").hasAnyRole("ADMIN", "DEVELOPER", "USER");
                    http.requestMatchers(HttpMethod.PUT, "/reservations/update").hasAnyRole("ADMIN", "DEVELOPER", "USER");
                    http.requestMatchers(HttpMethod.DELETE, "/reservations/delete/{idReservation}").hasAnyRole("ADMIN", "DEVELOPER", "USER");

                    // Endpoints de Flights (Gestin - Requiere permisos altos)
                    http.requestMatchers("/flights/**").hasAnyRole("ADMIN", "DEVELOPER");

                    /*---------------- Configuración del resto de endpoints ----------------*/
                    http.anyRequest().denyAll();
                })
                .build();
    }

    /**
     * Bean que proporciona el AuthenticationManager, utilizado para realizar la autenticacin.
     *
     * @param authenticationConfiguration La configuracin base de autenticacin.
     * @return El AuthenticationManager.
     * @throws Exception Si no se puede obtener el AuthenticationManager.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration authenticationConfiguration) throws Exception {
        return authenticationConfiguration.getAuthenticationManager();
    }

    /**
     * Define el proveedor de autenticacin que utiliza el UserDetailsService
     * personalizado y el PasswordEncoder.
     *
     * @param userDetailsService El servicio para cargar los detalles del usuario.
     * @return El proveedor de autenticacin basado en DAO.
     */
    @Bean
    public AuthenticationProvider authenticationProvider(UserDetailsServiceImpl userDetailsService) {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(userDetailsService);
        return provider;
    }

    /**
     * Define el codificador de contraseas utilizado en toda la aplicacin,
     * asegurando que las contraseas se almacenen como hashes BCrypt.
     *
     * @return BCryptPasswordEncoder.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

/*
    public static void main(String[] args) {
    //Encriptador
        System.out.println(new BCryptPasswordEncoder().encode("1234"));
    }
*/

}
