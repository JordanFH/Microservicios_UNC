package org.rsanchez.springcloud.msvc.usuarios.services;

import org.rsanchez.springcloud.msvc.usuarios.models.entity.Usuario;
import java.util.*;

public interface UsuarioService {
    List<Usuario> listar();
    Optional<Usuario> porId(Long id);
    Usuario guardar(Usuario usuario);
    void eliminar(Long id);

    List<Usuario> listaPorIds(Iterable<Long> ids);

    Optional<Usuario> porEmail(String email);
}
