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

import com.virtual.VirtualROOM1.Dtos.AvaliacoesDtos;
import com.virtual.VirtualROOM1.Modelo.AvaliacoesModelo;
import com.virtual.VirtualROOM1.Repositorios.AvaliacoesRepositorio;

import jakarta.validation.Valid;

@RestController
public class AvaliacoesControle {

    @Autowired
    AvaliacoesRepositorio avaliacoesRepositorio;

    @PostMapping("/Avaliacoes")
    public ResponseEntity<AvaliacoesModelo> saveAvaliacoes(@RequestBody @Valid AvaliacoesDtos avaliacoesDtos){
        var avaliacoesModelo = new AvaliacoesModelo();
        BeanUtils.copyProperties(avaliacoesDtos, avaliacoesModelo);
        return ResponseEntity.status(HttpStatus.CREATED).body(avaliacoesRepositorio.save(avaliacoesModelo));
    }

    @GetMapping("/Avaliacoes")
    public ResponseEntity<List<AvaliacoesModelo>> consultarAvaliacoes(){


    return ResponseEntity.status(HttpStatus.OK).body(avaliacoesRepositorio.findAll());
}
  
@PutMapping("/Avaliacoes/{id}")
    public ResponseEntity<AvaliacoesModelo> updateAvaliacoes(@PathVariable UUID id, @RequestBody @Valid AvaliacoesDtos avaliacoesDtos) {
        Optional<AvaliacoesModelo> optionalAvaliacoes = avaliacoesRepositorio.findById(id);
        if (optionalAvaliacoes.isPresent()) {
            AvaliacoesModelo existingAvaliacoes = optionalAvaliacoes.get();
            BeanUtils.copyProperties(avaliacoesDtos, existingAvaliacoes);
            AvaliacoesModelo updatedAvaliacoes = avaliacoesRepositorio.save(existingAvaliacoes);
            return ResponseEntity.ok(updatedAvaliacoes);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/Avaliacoes/{id}")
    public ResponseEntity<AvaliacoesModelo> getAvaliacoesById(@PathVariable UUID id) {
        Optional<AvaliacoesModelo> optionalAvaliacoes = avaliacoesRepositorio.findById(id);
        return optionalAvaliacoes.map(ResponseEntity::ok)
                                     .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/Avaliacoes/{id}")
    public ResponseEntity<Void> deleteAvaliacoes(@PathVariable UUID id) {
        Optional<AvaliacoesModelo> optionalAvaliacoes = avaliacoesRepositorio.findById(id);
        if (optionalAvaliacoes.isPresent()) {
            avaliacoesRepositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
}
