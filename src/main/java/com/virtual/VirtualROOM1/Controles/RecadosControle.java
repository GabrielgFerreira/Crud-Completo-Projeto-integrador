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

import com.virtual.VirtualROOM1.Dtos.RecadosDtos;
import com.virtual.VirtualROOM1.Modelo.RecadosModelo;
import com.virtual.VirtualROOM1.Repositorios.RecadosRepositorio;

import jakarta.validation.Valid;

@RestController
public class RecadosControle {

    @Autowired
    RecadosRepositorio recadosRepositorio;

    @PostMapping("/Recados")
    public ResponseEntity<RecadosModelo> saveRecados(@RequestBody @Valid RecadosDtos recadosDtos){
        var recadosModelo = new RecadosModelo();
        BeanUtils.copyProperties(recadosDtos, recadosModelo);
        return ResponseEntity.status(HttpStatus.CREATED).body(recadosRepositorio.save(recadosModelo));
    }

    @GetMapping("/Recados")
    public ResponseEntity<List<RecadosModelo>> consultarRecados(){


    return ResponseEntity.status(HttpStatus.OK).body(recadosRepositorio.findAll());
}
 
@PutMapping("/Recados/{id}")
    public ResponseEntity<RecadosModelo> updateRecados(@PathVariable UUID id, @RequestBody @Valid RecadosDtos recadosDtos) {
        Optional<RecadosModelo> optionalRecados = recadosRepositorio.findById(id);
        if (optionalRecados.isPresent()) {
            RecadosModelo existingRecados = optionalRecados.get();
            BeanUtils.copyProperties(recadosDtos, existingRecados);
            RecadosModelo updatedRecados = recadosRepositorio.save(existingRecados);
            return ResponseEntity.ok(updatedRecados);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/Recados/{id}")
    public ResponseEntity<RecadosModelo> getRecadosById(@PathVariable UUID id) {
        Optional<RecadosModelo> optionalRecados = recadosRepositorio.findById(id);
        return optionalRecados.map(ResponseEntity::ok)
                                     .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/Recados/{id}")
    public ResponseEntity<Void> deleteRecados(@PathVariable UUID id) {
        Optional<RecadosModelo> optionalRecados = recadosRepositorio.findById(id);
        if (optionalRecados.isPresent()) {
            recadosRepositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
}
