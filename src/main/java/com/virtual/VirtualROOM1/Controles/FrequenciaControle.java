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

import com.virtual.VirtualROOM1.Dtos.FrequenciaDtos;
import com.virtual.VirtualROOM1.Modelo.FrequenciaModelo;
import com.virtual.VirtualROOM1.Repositorios.FrequenciaRepositorio;

import jakarta.validation.Valid;

@RestController
public class FrequenciaControle {

    @Autowired
    FrequenciaRepositorio frequenciaRepositorio;

    @PostMapping("/Frequencia")
    public ResponseEntity<FrequenciaModelo> saveFrequencia(@RequestBody @Valid FrequenciaDtos frequenciaDtos){
        var frequenciaModelo = new FrequenciaModelo();
        BeanUtils.copyProperties(frequenciaDtos, frequenciaModelo);
        return ResponseEntity.status(HttpStatus.CREATED).body(frequenciaRepositorio.save(frequenciaModelo));
    }

    @GetMapping("/Frequencia")
    public ResponseEntity<List<FrequenciaModelo>> consultarFrequencia(){


    return ResponseEntity.status(HttpStatus.OK).body(frequenciaRepositorio.findAll());
}
  
@PutMapping("/Frequencia/{id}")
    public ResponseEntity<FrequenciaModelo> updateFrequencia(@PathVariable UUID id, @RequestBody @Valid FrequenciaDtos frequenciaDtos) {
        Optional<FrequenciaModelo> optionalFrequencia = frequenciaRepositorio.findById(id);
        if (optionalFrequencia.isPresent()) {
            FrequenciaModelo existingFrequencia = optionalFrequencia.get();
            BeanUtils.copyProperties(frequenciaDtos, existingFrequencia);
            FrequenciaModelo updatedFrequencia = frequenciaRepositorio.save(existingFrequencia);
            return ResponseEntity.ok(updatedFrequencia);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/Frequencia/{id}")
    public ResponseEntity<FrequenciaModelo> getFrequenciaById(@PathVariable UUID id) {
        Optional<FrequenciaModelo> optionalFrequencia = frequenciaRepositorio.findById(id);
        return optionalFrequencia.map(ResponseEntity::ok)
                                     .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/Frequencia/{id}")
    public ResponseEntity<Void> deleteFrequencia(@PathVariable UUID id) {
        Optional<FrequenciaModelo> optionalFrequencia = frequenciaRepositorio.findById(id);
        if (optionalFrequencia.isPresent()) {
            frequenciaRepositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
}
