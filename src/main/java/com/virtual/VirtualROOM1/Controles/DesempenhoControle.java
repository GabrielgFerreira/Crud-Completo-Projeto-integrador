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

import com.virtual.VirtualROOM1.Dtos.DesempenhoDtos;
import com.virtual.VirtualROOM1.Modelo.DesempenhoModelo;
import com.virtual.VirtualROOM1.Repositorios.DesempenhoRepositorio;

import jakarta.validation.Valid;

@RestController
public class DesempenhoControle {

    @Autowired
    DesempenhoRepositorio desempenhoRepositorio;

    @PostMapping("/Desempenho")
    public ResponseEntity<DesempenhoModelo> saveDesempenho(@RequestBody @Valid DesempenhoDtos desempenhoDtos){
        var desempenhoModelo = new DesempenhoModelo();
        BeanUtils.copyProperties(desempenhoDtos, desempenhoModelo);
        return ResponseEntity.status(HttpStatus.CREATED).body(desempenhoRepositorio.save(desempenhoModelo));
    }

    @GetMapping("/Desempenho")
    public ResponseEntity<List<DesempenhoModelo>> consultarDesempenho(){


    return ResponseEntity.status(HttpStatus.OK).body(desempenhoRepositorio.findAll());
}
  
@PutMapping("/Desempenho/{id}")
    public ResponseEntity<DesempenhoModelo> updateDesempenho(@PathVariable UUID id, @RequestBody @Valid DesempenhoDtos desempenhoDtos) {
        Optional<DesempenhoModelo> optionalDesempenho = desempenhoRepositorio.findById(id);
        if (optionalDesempenho.isPresent()) {
            DesempenhoModelo existingDesempenho = optionalDesempenho.get();
            BeanUtils.copyProperties(desempenhoDtos, existingDesempenho);
            DesempenhoModelo updatedDesempenho = desempenhoRepositorio.save(existingDesempenho);
            return ResponseEntity.ok(updatedDesempenho);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/Desempenho/{id}")
    public ResponseEntity<DesempenhoModelo> getDesempenhoById(@PathVariable UUID id) {
        Optional<DesempenhoModelo> optionalDesempenho = desempenhoRepositorio.findById(id);
        return optionalDesempenho.map(ResponseEntity::ok)
                                     .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/Desempenho/{id}")
    public ResponseEntity<Void> deleteDesempenho(@PathVariable UUID id) {
        Optional<DesempenhoModelo> optionalDesempenho = desempenhoRepositorio.findById(id);
        if (optionalDesempenho.isPresent()) {
            desempenhoRepositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
}
