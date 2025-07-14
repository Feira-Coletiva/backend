package br.edu.ifsc.sistemafeiracoletiva.service;


import br.edu.ifsc.sistemafeiracoletiva.model.domain.Cliente;
import br.edu.ifsc.sistemafeiracoletiva.repository.ClienteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

/**
 * Serviço responsável pela autenticação do cliente:
 * ✅ Validar credenciais (email e senha).
 * ✅ Gerar JWT ao autenticar com sucesso.
 */
@Service // Componente Spring injetável
public class AuthService {

    @Autowired
    private ClienteRepository clienteRepository; // Para buscar o cliente no banco

    @Autowired
    private JwtService jwtService; // Para gerar e validar tokens

    @Autowired
    private PasswordEncoder passwordEncoder; // Para validar senha criptografada

    /**
     * Autentica o cliente usando email e senha.
     * Se válidos, retorna o token JWT gerado.
     *
     * @param email do cliente.
     * @param senha em texto plano fornecida pelo cliente.
     * @return token JWT gerado.
     * @throws RuntimeException caso credenciais sejam inválidas.
     */
    public String autenticarCliente(String email, String senha) {
        // Busca cliente pelo email
        Cliente cliente = clienteRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Cliente não encontrado com o email fornecido."));

        // Valida a senha usando o PasswordEncoder
        if (!passwordEncoder.matches(senha, cliente.getSenha())) {
            throw new RuntimeException("Senha incorreta.");
        }

        // Gera o token JWT usando o email do cliente
        return jwtService.generateToken(email);
    }
}
