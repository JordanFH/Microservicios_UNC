package org.rsanchez.springcloud.msvc.usuarios.controllers;

import jakarta.validation.Valid;
import org.rsanchez.springcloud.msvc.usuarios.models.entity.Usuario;
import org.rsanchez.springcloud.msvc.usuarios.services.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/api/usuario")
public class UsuarioController {
    @Autowired
    private UsuarioService service;
    @GetMapping
    public List<Usuario> listar(){
        return service.listar();
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle (@PathVariable Long id){
        Optional<Usuario> opti = service.porId(id);
        if(opti.isPresent()){
            return ResponseEntity.ok(opti.get());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<?> crear (@Valid @RequestBody Usuario usuario, BindingResult result){
        if(service.porEmail(usuario.getEmail()).isPresent()){
            return ResponseEntity.badRequest().body(Collections
                    .singletonMap("Mensaje","Ya existe un usuario con ese email"));
        }
        if(result.hasErrors()){
            return validar(result);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuario));
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editar (@Valid @RequestBody Usuario usuario, BindingResult result, @PathVariable Long id){
        if(result.hasErrors()){
            return validar(result);
        }
        Optional<Usuario> opti = service.porId(id);
        if(opti.isPresent()){
            Usuario usuarioDb = opti.get();
            if(!usuario.getEmail().equalsIgnoreCase(usuarioDb.getEmail())
                    && service.porEmail(usuario.getEmail()).isPresent()){
                return ResponseEntity.badRequest().body(Collections
                        .singletonMap("Mensaje","Ya existe un usuario con ese email"));
            }
            usuarioDb.setNombre(usuario.getNombre());
            usuarioDb.setEmail(usuario.getEmail());
            usuarioDb.setPassword(usuario.getPassword());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(usuarioDb));
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eleminar (@PathVariable Long id){
        Optional<Usuario> opti = service.porId(id);
        if(opti.isPresent()){
            service.eliminar(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/usuario-por-curso")
    public ResponseEntity<?> obtenerAlumnosPorCurso(@RequestParam List<Long> ids){
        return ResponseEntity.ok(service.listaPorIds(ids));
    }

    private static ResponseEntity<Map<String, String>> validar(BindingResult result) {
        Map<String, String> errores = new HashMap<>();
        result.getFieldErrors().forEach(err ->{
            errores.put(err.getField(),"El campo "+err.getField()
                    +" "+err.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(errores);
    }
}
