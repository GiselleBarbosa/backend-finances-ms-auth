package br.com.barbosa.services;

import br.com.barbosa.dtos.AuthRequestDTO;
import br.com.barbosa.dtos.AuthResponseDTO;
import br.com.barbosa.entities.User;
import br.com.barbosa.exceptions.InvalidCredentialsException;
import br.com.barbosa.repositories.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;


@Service
public class AuthService {

    private final UserRepository userRepository;
    private final BCryptPasswordEncoder passwordEncoder;
    private final JwtService jwtService;

    private static final Logger logger = LoggerFactory.getLogger(AuthService.class);

    @Autowired
    public AuthService(
            UserRepository userRepository,
            BCryptPasswordEncoder passwordEncoder,
            JwtService jwtService
    ) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtService = jwtService;
    }

    public AuthResponseDTO authenticate(AuthRequestDTO authRequest) {
        String email = authRequest.getEmail();
        String password = authRequest.getPassword();

        if (email == null || email.isEmpty() || password == null || password.isEmpty()) {
            logger.warn("Email ou senha inválidos.");
            throw new InvalidCredentialsException("Email ou senha inválidos.");
        }

        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> {
                    logger.warn("Usuário não encontrado com o email: {}", email);
                    return new InvalidCredentialsException("Usuário não encontrado com o e-mail: " + email);
                });

        if (!passwordEncoder.matches(password.trim(), user.getPassword())) {
            logger.warn("Senha incorreta para o email: {}", email);
            throw new InvalidCredentialsException("Senha incorreta.");
        }

        String token = jwtService.generateToken(user);
        logger.info("Login bem-sucedido para o email: {}", email);

        return new AuthResponseDTO(token);
    }
}