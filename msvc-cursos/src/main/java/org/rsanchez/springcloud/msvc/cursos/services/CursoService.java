package org.rsanchez.springcloud.msvc.cursos.services;

import org.rsanchez.springcloud.msvc.cursos.models.Usuario;
import org.rsanchez.springcloud.msvc.cursos.models.entity.Curso;
import java.util.List;
import java.util.Optional;

public interface CursoService {
    List<Curso> listar();
    Optional<Curso> porId(Long id);
    Curso guardar(Curso curso);
    void eliminar(Long id);
    //Metodos remotos relacionados con el HTTPClient
    // (para la comunicacion de microservicios por medio de apiRest)
    Optional<Usuario> asignarUsuario(Usuario usuario, Long id);
    Optional<Usuario> crearUsuario(Usuario usuario, Long id);
    Optional<Usuario> eliminarUsuario(Usuario usuario, Long id);
    Optional<Curso> porIdConUsuarios(Long id);
}
