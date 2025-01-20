package dev.jerry.ChallengeForoHubProyecto.domain.topico;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record RegisterTopicData(
        @NotNull Long usuario_id,
        @NotBlank String titulo,
        @NotBlank String mensaje,
        @NotBlank String curso,
        @NotBlank String autor
) {
}