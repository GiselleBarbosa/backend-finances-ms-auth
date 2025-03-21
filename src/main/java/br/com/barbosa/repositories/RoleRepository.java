package br.com.barbosa.repositories;

import br.com.barbosa.entities.Role;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

public interface RoleRepository extends MongoRepository<Role, String> {
    Optional<Role> findByRoleName(String roleName);
}
