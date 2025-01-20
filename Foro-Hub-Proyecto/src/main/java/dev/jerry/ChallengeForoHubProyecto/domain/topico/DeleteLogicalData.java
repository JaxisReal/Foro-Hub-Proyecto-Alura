package dev.jerry.ChallengeForoHubProyecto.domain.topico;

import java.time.LocalDateTime;

public record DeleteLogicalData(
        Long id,
        String autor,
        String titulo,
        LocalDateTime fecha_de_creacion,
        Boolean status
) {
}