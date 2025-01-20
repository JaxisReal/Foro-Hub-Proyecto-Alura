package dev.jerry.ChallengeForoHubProyecto.domain.topico;

import jakarta.validation.constraints.NotNull;

public record UpdateTopicData(
        @NotNull Long id,
        String autor,
        String titulo,
        String mensaje
) {
}