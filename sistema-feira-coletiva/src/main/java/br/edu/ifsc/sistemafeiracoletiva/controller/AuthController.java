package br.edu.ifsc.sistemafeiracoletiva.controller;

import br.edu.ifsc.sistemafeiracoletiva.dto.LoginRequestDTO;
import br.edu.ifsc.sistemafeiracoletiva.dto.LoginResponseDTO;
import br.edu.ifsc.sistemafeiracoletiva.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * Controller responsável por endpoints de autenticação (/auth).
 */
@RestController // Define como REST Controller
@RequestMapping("/auth") // Endereço base dos endpoints de autenticação
@CrossOrigin(origins = "http://localhost:5500") // Liberar CORS enquanto estiver testando
public class AuthController {

    @Autowired
    private AuthService authService;

    /**
     * Endpoint para login do cliente.
     * Recebe email e senha, retorna token JWT.
     */
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDTO> login(@RequestBody LoginRequestDTO loginRequest) {
        String token = authService.autenticarCliente(loginRequest.getEmail(), loginRequest.getSenha());
        return ResponseEntity.ok(new LoginResponseDTO(token));
    }
}
