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

import com.virtual.VirtualROOM1.Dtos.NotasDtos;
import com.virtual.VirtualROOM1.Modelo.NotasModelo;
import com.virtual.VirtualROOM1.Repositorios.NotasRepositorio;

import jakarta.validation.Valid;

@RestController
public class NotasControle {

    @Autowired
    NotasRepositorio notasRepositorio;

    @PostMapping("/Notas")
    public ResponseEntity<NotasModelo> saveNotas(@RequestBody @Valid NotasDtos notasDtos){
        var notasModelo = new NotasModelo();
        BeanUtils.copyProperties(notasDtos, notasModelo);
        return ResponseEntity.status(HttpStatus.CREATED).body(notasRepositorio.save(notasModelo));
    }

    @GetMapping("/Notas")
    public ResponseEntity<List<NotasModelo>> consultarNotas(){


    return ResponseEntity.status(HttpStatus.OK).body(notasRepositorio.findAll());
}
  
@PutMapping("/Notas/{id}")
    public ResponseEntity<NotasModelo> updateNotas(@PathVariable UUID id, @RequestBody @Valid NotasDtos notasDtos) {
        Optional<NotasModelo> optionalNotas = notasRepositorio.findById(id);
        if (optionalNotas.isPresent()) {
            NotasModelo existingNotas = optionalNotas.get();
            BeanUtils.copyProperties(notasDtos, existingNotas);
            NotasModelo updatedNotas = notasRepositorio.save(existingNotas);
            return ResponseEntity.ok(updatedNotas);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/Notas/{id}")
    public ResponseEntity<NotasModelo> getNotasById(@PathVariable UUID id) {
        Optional<NotasModelo> optionalNotas = notasRepositorio.findById(id);
        return optionalNotas.map(ResponseEntity::ok)
                                     .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/Notas/{id}")
    public ResponseEntity<Void> deleteNotas(@PathVariable UUID id) {
        Optional<NotasModelo> optionalNotas = notasRepositorio.findById(id);
        if (optionalNotas.isPresent()) {
            notasRepositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
}