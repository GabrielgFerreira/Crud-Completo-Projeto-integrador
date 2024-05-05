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

import com.virtual.VirtualROOM1.Dtos.ComunicacaoDtos;
import com.virtual.VirtualROOM1.Modelo.ComunicacaoModelo;
import com.virtual.VirtualROOM1.Repositorios.ComunicacaoRepositorio;

import jakarta.validation.Valid;

@RestController
public class ComunicacaoControle {

    @Autowired
    ComunicacaoRepositorio comunicacaoRepositorio;

    @PostMapping("/Comunicacao")
    public ResponseEntity<ComunicacaoModelo> saveComunicacao(@RequestBody @Valid ComunicacaoDtos comunicacaoDtos){
        var comunicacaoModelo = new ComunicacaoModelo();
        BeanUtils.copyProperties(comunicacaoDtos, comunicacaoModelo);
        return ResponseEntity.status(HttpStatus.CREATED).body(comunicacaoRepositorio.save(comunicacaoModelo));
    }

    @GetMapping("/Comunicacao")
    public ResponseEntity<List<ComunicacaoModelo>> consultarComunicacao(){


    return ResponseEntity.status(HttpStatus.OK).body(comunicacaoRepositorio.findAll());
}
  
@PutMapping("/Comunicacao/{id}")
    public ResponseEntity<ComunicacaoModelo> updateComunicacao(@PathVariable UUID id, @RequestBody @Valid ComunicacaoDtos comunicacaoDtos) {
        Optional<ComunicacaoModelo> optionalComunicacao = comunicacaoRepositorio.findById(id);
        if (optionalComunicacao.isPresent()) {
            ComunicacaoModelo existingComunicacao = optionalComunicacao.get();
            BeanUtils.copyProperties(comunicacaoDtos, existingComunicacao);
            ComunicacaoModelo updatedComunicacao = comunicacaoRepositorio.save(existingComunicacao);
            return ResponseEntity.ok(updatedComunicacao);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/Comunicacao/{id}")
    public ResponseEntity<ComunicacaoModelo> getComunicacaoById(@PathVariable UUID id) {
        Optional<ComunicacaoModelo> optionalComunicacao = comunicacaoRepositorio.findById(id);
        return optionalComunicacao.map(ResponseEntity::ok)
                                     .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/Comunicacao/{id}")
    public ResponseEntity<Void> deleteComunicacao(@PathVariable UUID id) {
        Optional<ComunicacaoModelo> optionalComunicacao = comunicacaoRepositorio.findById(id);
        if (optionalComunicacao.isPresent()) {
            comunicacaoRepositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
}
