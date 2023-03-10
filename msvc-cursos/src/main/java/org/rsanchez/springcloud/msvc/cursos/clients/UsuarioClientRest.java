package org.rsanchez.springcloud.msvc.cursos.clients;

import org.rsanchez.springcloud.msvc.cursos.models.Usuario;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "msvc-usuarios", url="host.docker.internal:8001/api/usuario")
public interface UsuarioClientRest {
    @GetMapping("/{id}")
    Usuario detalle(@PathVariable Long id);
    @PostMapping
    Usuario crear(@RequestBody Usuario usuario);
    @GetMapping("/usuario-por-curso")
    List<Usuario> obtenerAlumnosPorCurso(@RequestParam Iterable<Long> ids);
}
