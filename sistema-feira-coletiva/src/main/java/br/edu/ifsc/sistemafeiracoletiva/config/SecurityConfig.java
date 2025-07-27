package br.edu.ifsc.sistemafeiracoletiva.config;

import br.edu.ifsc.sistemafeiracoletiva.service.ClienteService;
import br.edu.ifsc.sistemafeiracoletiva.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuração completa de segurança para JWT.
 */
@Configuration
@EnableWebSecurity // Certifique-se de que esta anotação está presente
public class SecurityConfig {

    // Certifique-se de que essas dependências estão corretas e que os pacotes estão ajustados
    // @Autowired
    // private JwtService jwtService;
    // @Autowired
    // private ClienteService clienteService;


    @Bean
    public JwtAuthenticationFilter jwtAuthenticationFilter(JwtService jwtService, ClienteService clienteService) {
        return new JwtAuthenticationFilter(jwtService, clienteService);
    }

    /**
     * Bean de PasswordEncoder (já utilizado no AuthService e ClienteService).
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Bean do AuthenticationManager caso necessite em outras partes do sistema.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Configuração da cadeia de filtros de segurança.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http,
                                                   JwtAuthenticationFilter jwtAuthenticationFilter) throws Exception {
        http
                .csrf(csrf -> csrf.disable())
                // .cors(cors -> cors.disable()) // REMOVIDO: Não desabilite o CORS aqui!
                .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/api/**").permitAll() // Permite todas as requisições para /api/** (incluindo /api/clientes)
                        .requestMatchers("/auth/**").permitAll() // Permite todas as requisições para /auth/**
                        .anyRequest().permitAll() // Permite todas as outras requisições
                )
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

        return http.build();
    }
}

