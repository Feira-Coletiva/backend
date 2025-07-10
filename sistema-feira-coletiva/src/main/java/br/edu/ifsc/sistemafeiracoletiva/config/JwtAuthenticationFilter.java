package br.edu.ifsc.sistemafeiracoletiva.config;

import br.edu.ifsc.sistemafeiracoletiva.model.domain.Cliente;
import br.edu.ifsc.sistemafeiracoletiva.service.ClienteService;
import br.edu.ifsc.sistemafeiracoletiva.service.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filtro responsável por interceptar requisições e validar o JWT enviado no cabeçalho Authorization.
 * Se válido, autentica o cliente no contexto de segurança do Spring.
 */
@Component // Torna o filtro gerenciado pelo Spring
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final ClienteService clienteService;

    @Autowired
    public JwtAuthenticationFilter(JwtService jwtService, ClienteService clienteService) {
        this.jwtService = jwtService;
        this.clienteService = clienteService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        // Extrai o cabeçalho Authorization
        String authHeader = request.getHeader("Authorization");

        String token = null;
        String email = null;

        // Verifica se o cabeçalho contém um Bearer token
        if (authHeader != null && authHeader.startsWith("Bearer ")) {
            token = authHeader.substring(7); // Remove "Bearer " do início

            // Valida o token
            if (jwtService.validateToken(token)) {
                // Extrai o email do token
                email = jwtService.extractEmail(token);

                // Verifica se o usuário ainda não está autenticado no contexto
                if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

                    // Busca o cliente no banco
                    Cliente cliente = clienteService.buscarClientePorEmail(email);

                    // Cria o objeto de autenticação para o contexto de segurança
                    UsernamePasswordAuthenticationToken authToken =
                            new UsernamePasswordAuthenticationToken(
                                    cliente, // principal (objeto autenticado)
                                    null,    // credentials
                                    null     // authorities (caso use perfis, adicione aqui)
                            );

                    authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                    // Registra o usuário autenticado no contexto de segurança
                    SecurityContextHolder.getContext().setAuthentication(authToken);
                }
            }
        }

        // Continua o fluxo normal da requisição
        filterChain.doFilter(request, response);
    }
}
