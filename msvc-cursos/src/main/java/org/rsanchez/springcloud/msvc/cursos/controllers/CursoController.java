package org.rsanchez.springcloud.msvc.cursos.controllers;

import feign.FeignException;
import org.rsanchez.springcloud.msvc.cursos.models.Usuario;
import org.rsanchez.springcloud.msvc.cursos.models.entity.Curso;
import org.rsanchez.springcloud.msvc.cursos.services.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/curso")
public class CursoController {
    @Autowired
    private CursoService service;
    @GetMapping
    public ResponseEntity<List<Curso>> listar(){
        return ResponseEntity.ok(service.listar());
    }
    @GetMapping("/{id}")
    public ResponseEntity<?> detalle(@PathVariable Long id){
        Optional<Curso> op = service.porId(id);
        if(op.isPresent()){
            return ResponseEntity.ok(op.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping
    public ResponseEntity<?> crear(@RequestBody Curso curso){
        Curso cursoDb = service.guardar(curso);
        return ResponseEntity.status(HttpStatus.CREATED).body(cursoDb);
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@RequestBody Curso curso, @PathVariable Long id){
        Optional<Curso> op = service.porId(id);
        if(op.isPresent()){
            Curso curDb = op.get();
            curDb.setNombre(curso.getNombre());
            return ResponseEntity.status(HttpStatus.CREATED).body(service.guardar(curDb));
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> eliminar(@PathVariable Long id){
        Optional<Curso> op = service.porId(id);
        if(op.isPresent()){
            service.eliminar(op.get().getId());
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
    //Metodos remotos de comunicación
    @PutMapping("/asignar-usuario/{cursoId}")
    public ResponseEntity<?> asignarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> o;
        try{
            o=service.asignarUsuario(usuario, cursoId);
        }
        catch(FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Mensaje","No existe usuario con ese id "+
                            e.getMessage()));
        }
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }
    @PostMapping("/crear-usuario/{cursoId}")
    public ResponseEntity<?> crearUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> o;
        try{
            o=service.crearUsuario(usuario, cursoId);
        }
        catch(FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Mensaje","No se pudo crear el usuario "+
                            "o falla en la comunicación: "+e.getMessage()));
        }
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }
    @DeleteMapping("/eliminar-usuario/{cursoId}")
    public ResponseEntity<?> eliminarUsuario(@RequestBody Usuario usuario, @PathVariable Long cursoId){
        Optional<Usuario> o;
        try{
            o=service.eliminarUsuario(usuario, cursoId);
        }
        catch(FeignException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(Collections.singletonMap("Mensaje","No existe usuario con ese id "+
                            " o error en la comunicación: "+e.getMessage()));
        }
        if(o.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).body(o.get());
        }
        return ResponseEntity.notFound().build();
    }
    @GetMapping("/curUsers/{id}")
    public ResponseEntity<?> detalleCursoUsuarios(@PathVariable Long id){
        Optional<Curso> op = service.porIdConUsuarios(id);
        if(op.isPresent()){
            return ResponseEntity.ok(op.get());
        }
        return ResponseEntity.notFound().build();
    }
}
