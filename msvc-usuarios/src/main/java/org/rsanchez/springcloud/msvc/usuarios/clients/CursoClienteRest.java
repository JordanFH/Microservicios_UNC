package org.rsanchez.springcloud.msvc.usuarios.clients;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name="msvc-cursos", url="host.docker.internal:8002/api/curso")
public interface CursoClienteRest {
    @DeleteMapping("/eliminar-curUser/{id}")
    public ResponseEntity<?> eliminarCursoUsuarioPorId(@PathVariable Long id);
}
