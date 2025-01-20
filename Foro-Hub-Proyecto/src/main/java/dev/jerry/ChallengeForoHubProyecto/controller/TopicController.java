package dev.jerry.ChallengeForoHubProyecto.controller;

import dev.jerry.ChallengeForoHubProyecto.domain.topico.*;
import dev.jerry.ChallengeForoHubProyecto.domain.usuarios.UserRepository;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/topics")
@SecurityRequirement(name = "bearer-key")
public class TopicController {

    @Autowired
    private TopicRepository topicRepository;

    @Autowired
    private UserRepository usuarioRepositorio;

    @PostMapping
    @Transactional
    public ResponseEntity<TopicResponseData> registrarTopico(@RequestBody @Valid RegisterTopicData datosRegistroTopico, UriComponentsBuilder uriComponentsBuilder) {
        var usuario = usuarioRepositorio.findById(datosRegistroTopico.usuario_id()).orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        Topic newTopic = topicRepository.save(new Topic(datosRegistroTopico, usuario));

        TopicResponseData datosRespuestaTopico = new TopicResponseData(newTopic.getId(), newTopic.getAutor(), newTopic.getTitulo(), newTopic.getMensaje(), newTopic.getFecha_de_creacion());

        URI url = uriComponentsBuilder.path("/topics/{id}").buildAndExpand(newTopic.getId()).toUri();
        return ResponseEntity.created(url).body(datosRespuestaTopico);
    }

    @GetMapping
    public ResponseEntity<Page<TopicListData>> listarTopicos(@PageableDefault(size = 2) Pageable pageable) {
        return ResponseEntity.ok(topicRepository.findByStatusTrue(pageable).map(TopicListData::new));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TopicResponseData> obtenerDatosTopico(@PathVariable Long id) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> new RuntimeException("Topico no encontrado"));
        var datosTopico = new TopicResponseData(topic.getId(), topic.getAutor(), topic.getTitulo(), topic.getMensaje(), topic.getFecha_de_creacion());
        return ResponseEntity.ok(datosTopico);
    }

    @PutMapping
    @Transactional
    public ResponseEntity<TopicResponseData> actualizarTopico(@RequestBody @Valid UpdateTopicData datosActualizarTopico) {
        Topic topic = topicRepository.findById(datosActualizarTopico.id()).orElseThrow(() -> new RuntimeException("Topico no encontrado"));
        topic.actualizarDatos(datosActualizarTopico);
        return ResponseEntity.ok(new TopicResponseData(topic.getId(), topic.getAutor(), topic.getTitulo(), topic.getMensaje(), topic.getFecha_de_creacion()));
    }

    @PutMapping("/{id}")
    @Transactional
    public ResponseEntity<TopicResponseData> actualizarTopicoPorId(@RequestBody @Valid UpdateSpecificTopicData datosActulizarTopicoEspecifico, @PathVariable Long id) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> new RuntimeException("Topico no encontrado"));
        topic.actualizarDatosTopicoEspecifico(datosActulizarTopicoEspecifico);
        return ResponseEntity.ok(new TopicResponseData(topic.getId(), topic.getAutor(), topic.getTitulo(), topic.getMensaje(), topic.getFecha_de_creacion()));
    }

    @DeleteMapping("/{id}")
    @Transactional
    public ResponseEntity<DeleteLogicalData> eliminarTopico(@PathVariable Long id) {
        Topic topic = topicRepository.findById(id).orElseThrow(() -> new RuntimeException("Topico no encontrado"));
        topic.desactivarTopico();
        return ResponseEntity.ok(new DeleteLogicalData(topic.getId(), topic.getAutor(), topic.getTitulo(), topic.getFecha_de_creacion(), topic.getStatus()));
    }
}