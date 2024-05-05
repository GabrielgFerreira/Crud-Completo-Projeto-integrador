package com.virtual.VirtualROOM1.Controles;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.virtual.VirtualROOM1.Dtos.UsuariosDtos;
import com.virtual.VirtualROOM1.Modelo.UsuariosModelo;
import com.virtual.VirtualROOM1.Repositorios.UsuariosRepositorio;

import jakarta.validation.Valid;

@RestController
public class UsuariosControle {

    @Autowired
    UsuariosRepositorio usuariosRepositorio;

    @PostMapping("/Usuarios")
    public ResponseEntity<UsuariosModelo> saveUsuarios(@RequestBody @Valid UsuariosDtos usuariosDtos){
        var usuariosModelo = new UsuariosModelo();
        BeanUtils.copyProperties(usuariosDtos, usuariosModelo);
        return ResponseEntity.status(HttpStatus.CREATED).body(usuariosRepositorio.save(usuariosModelo));
    }

    @GetMapping("/Usuarios")
    public ResponseEntity<List<UsuariosModelo>> consultarUsuarios(){


    return ResponseEntity.status(HttpStatus.OK).body(usuariosRepositorio.findAll());
}
  
@PutMapping("/Usuarios/{id}")
    public ResponseEntity<UsuariosModelo> updateUsuarios(@PathVariable UUID id, @RequestBody @Valid UsuariosDtos usuariosDtos) {
        Optional<UsuariosModelo> optionalUsuarios = usuariosRepositorio.findById(id);
        if (optionalUsuarios.isPresent()) {
            UsuariosModelo existingUsuarios = optionalUsuarios.get();
            BeanUtils.copyProperties(usuariosDtos, existingUsuarios);
            UsuariosModelo updatedUsuarios = usuariosRepositorio.save(existingUsuarios);
            return ResponseEntity.ok(updatedUsuarios);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/Usuarios/{id}")
    public ResponseEntity<UsuariosModelo> getUsuariosById(@PathVariable UUID id) {
        Optional<UsuariosModelo> optionalUsuarios = usuariosRepositorio.findById(id);
        return optionalUsuarios.map(ResponseEntity::ok)
                                     .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/Usuarios/{id}")
    public ResponseEntity<Void> deleteUsuarios(@PathVariable UUID id) {
        Optional<UsuariosModelo> optionalUsuarios = usuariosRepositorio.findById(id);
        if (optionalUsuarios.isPresent()) {
            usuariosRepositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
}
