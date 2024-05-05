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

import com.virtual.VirtualROOM1.Dtos.ConteudosDtos;
import com.virtual.VirtualROOM1.Modelo.ConteudosModelo;
import com.virtual.VirtualROOM1.Repositorios.ConteudosRepositorio;

import jakarta.validation.Valid;

@RestController
public class ConteudosControle {

    @Autowired
    ConteudosRepositorio conteudosRepositorio;

    @PostMapping("/Conteudos")
    public ResponseEntity<ConteudosModelo> saveConteudos(@RequestBody @Valid ConteudosDtos conteudosDtos){
        var conteudosModelo = new ConteudosModelo();
        BeanUtils.copyProperties(conteudosDtos, conteudosModelo);
        return ResponseEntity.status(HttpStatus.CREATED).body(conteudosRepositorio.save(conteudosModelo));
    }

    @GetMapping("/Conteudos")
    public ResponseEntity<List<ConteudosModelo>> consultarConteudos(){


    return ResponseEntity.status(HttpStatus.OK).body(conteudosRepositorio.findAll());
}
  
@PutMapping("/Conteudos/{id}")
    public ResponseEntity<ConteudosModelo> updateConteudos(@PathVariable UUID id, @RequestBody @Valid ConteudosDtos conteudosDtos) {
        Optional<ConteudosModelo> optionalConteudos = conteudosRepositorio.findById(id);
        if (optionalConteudos.isPresent()) {
            ConteudosModelo existingConteudos = optionalConteudos.get();
            BeanUtils.copyProperties(conteudosDtos, existingConteudos);
            ConteudosModelo updatedConteudos = conteudosRepositorio.save(existingConteudos);
            return ResponseEntity.ok(updatedConteudos);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/Conteudos/{id}")
    public ResponseEntity<ConteudosModelo> getConteudosById(@PathVariable UUID id) {
        Optional<ConteudosModelo> optionalConteudos = conteudosRepositorio.findById(id);
        return optionalConteudos.map(ResponseEntity::ok)
                                     .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/Conteudos/{id}")
    public ResponseEntity<Void> deleteConteudos(@PathVariable UUID id) {
        Optional<ConteudosModelo> optionalConteudos = conteudosRepositorio.findById(id);
        if (optionalConteudos.isPresent()) {
            conteudosRepositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
}
