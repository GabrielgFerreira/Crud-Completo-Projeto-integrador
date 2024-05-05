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

import com.virtual.VirtualROOM1.Dtos.AtividadesDtos;
import com.virtual.VirtualROOM1.Modelo.AtividadesModelo;
import com.virtual.VirtualROOM1.Repositorios.AtividadesRepositorio;

import jakarta.validation.Valid;

@RestController
public class AtividadesControle {

    @Autowired
    AtividadesRepositorio atividadesRepositorio;

    @PostMapping("/Atividades")
    public ResponseEntity<AtividadesModelo> saveAtividades(@RequestBody @Valid AtividadesDtos atividadesDtos){
        var atividadesModelo = new AtividadesModelo();
        BeanUtils.copyProperties(atividadesDtos, atividadesModelo);
        return ResponseEntity.status(HttpStatus.CREATED).body(atividadesRepositorio.save(atividadesModelo));
    }

    @GetMapping("/Atividades")
        public ResponseEntity<List<AtividadesModelo>> consultarAtividades() {

        return ResponseEntity.status(HttpStatus.OK).body(atividadesRepositorio.findAll());
    }

    
    @PutMapping("/Atividades/{id}")
    public ResponseEntity<AtividadesModelo> updateAtividades(@PathVariable UUID id, @RequestBody @Valid AtividadesDtos atividadesDtos) {
        Optional<AtividadesModelo> optionalAtividades = atividadesRepositorio.findById(id);
        if (optionalAtividades.isPresent()) {
            AtividadesModelo existingAtividades = optionalAtividades.get();
            BeanUtils.copyProperties(atividadesDtos, existingAtividades);
            AtividadesModelo updatedAtividades = atividadesRepositorio.save(existingAtividades);
            return ResponseEntity.ok(updatedAtividades);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/Atividades/{id}")
    public ResponseEntity<AtividadesModelo> getAtividadeById(@PathVariable UUID id) {
        Optional<AtividadesModelo> optionalAtividades = atividadesRepositorio.findById(id);
        return optionalAtividades.map(ResponseEntity::ok)
                                     .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/Atividades/{id}")
    public ResponseEntity<Void> deleteAluno(@PathVariable UUID id) {
        Optional<AtividadesModelo> optionalAtividades = atividadesRepositorio.findById(id);
        if (optionalAtividades.isPresent()) {
            atividadesRepositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
}
