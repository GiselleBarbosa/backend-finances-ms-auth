package br.com.barbosa.dtos;

import br.com.barbosa.entities.User;

public record AuthUserResponseDTO(String id, String name, String email) {

    public static AuthUserResponseDTO fromEntity(User user) {
        return new AuthUserResponseDTO(user.getId(), user.getName(), user.getEmail());
    }
}