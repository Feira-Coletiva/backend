package br.edu.ifsc.sistemafeiracoletiva.service;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.Date;

/**
 * Serviço responsável por:
 * ✅ Gerar tokens JWT.
 * ✅ Validar tokens JWT.
 * ✅ Extrair informações do token (ex: email).
 */
@Service // Torna a classe gerenciada pelo Spring (injeção automática onde precisar)
public class JwtService {

    /**
     * Chave secreta usada para assinar e validar os tokens.
     * Defina no application.properties:
     * jwt.secret=chave_super_secreta_com_mais_de_32_caracteres
     */
    @Value("${jwt.secret}")
    private String jwtSecret;

    /**
     * Tempo de expiração em milissegundos.
     * Ex: 86400000 ms = 1 dia
     * Defina no application.properties:
     * jwt.expiration=86400000
     */
    @Value("${jwt.expiration}")
    private Long jwtExpiration;

    /**
     * Gera um token JWT para o email do usuário autenticado.
     * @param email do cliente autenticado.
     * @return token JWT gerado.
     */
    public String generateToken(String email) {
        Date now = new Date(); // Data atual
        Date expiryDate = new Date(now.getTime() + jwtExpiration); // Data de expiração

        // Chave de assinatura
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        // Criação do token
        return Jwts.builder()
                .setSubject(email) // Guarda o email como subject
                .setIssuedAt(now)  // Data de emissão
                .setExpiration(expiryDate) // Data de expiração
                .signWith(key, SignatureAlgorithm.HS256) // Algoritmo de assinatura
                .compact(); // Converte para String
    }

    /**
     * Valida se o token JWT é válido e não expirado.
     * @param token JWT recebido.
     * @return true se válido, false se inválido ou expirado.
     */
    public boolean validateToken(String token) {
        try {
            Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

            // Faz o parse do token para verificar assinatura e expiração
            Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            return true;
        } catch (JwtException | IllegalArgumentException e) {
            // Token inválido, expirado ou mal formado
            return false;
        }
    }

    /**
     * Extrai o email do usuário a partir do token JWT.
     * @param token JWT recebido.
     * @return email contido no subject do token.
     */
    public String extractEmail(String token) {
        Key key = Keys.hmacShaKeyFor(jwtSecret.getBytes());

        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return claims.getSubject();
    }
}
