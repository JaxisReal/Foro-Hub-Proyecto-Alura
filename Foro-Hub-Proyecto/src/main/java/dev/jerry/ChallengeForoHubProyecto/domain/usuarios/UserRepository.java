package dev.jerry.ChallengeForoHubProyecto.domain.usuarios;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

public interface UserRepository extends JpaRepository<Usuario, Long> {
    UserDetails findByEmail(String email);
}
