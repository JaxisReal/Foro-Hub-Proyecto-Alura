package dev.jerry.ChallengeForoHubProyecto.domain.topico;

import java.time.LocalDateTime;

public record TopicListData(
        Long id,
        String autor,
        String titulo,
        String mensaje,
        LocalDateTime fechaDeCreacion
) {
    public TopicListData(Topic topico) {
        this(topico.getId(), topico.getAutor(), topico.getTitulo(), topico.getMensaje(), topico.getFecha_de_creacion());
    }
}