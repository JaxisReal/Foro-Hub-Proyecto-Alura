package dev.jerry.ChallengeForoHubProyecto.domain.topico;

import java.time.LocalDateTime;

public record TopicResponseData(
        Long id,
        String autor,
        String titulo,
        String mensaje,
        LocalDateTime fechaDeCreacion
) {
}