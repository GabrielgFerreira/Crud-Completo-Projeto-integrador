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


import com.virtual.VirtualROOM1.Dtos.ForumDtos;
import com.virtual.VirtualROOM1.Modelo.ForumModelo;
import com.virtual.VirtualROOM1.Repositorios.ForumRepositorio;

import jakarta.validation.Valid;

@RestController
public class ForumControle {

    @Autowired
    ForumRepositorio forumRepositorio;

    @PostMapping("/Forum")
    public ResponseEntity<ForumModelo> saveForum(@RequestBody @Valid ForumDtos forumDtos){
        var forumModelo = new ForumModelo();
        BeanUtils.copyProperties(forumDtos, forumModelo);
        return ResponseEntity.status(HttpStatus.CREATED).body(forumRepositorio.save(forumModelo));
    }

    @GetMapping("/Forum")
    public ResponseEntity<List<ForumModelo>> consultarForum(){


    return ResponseEntity.status(HttpStatus.OK).body(forumRepositorio.findAll());
}
  
@PutMapping("/Forum/{id}")
    public ResponseEntity<ForumModelo> updateForum(@PathVariable UUID id, @RequestBody @Valid ForumDtos forumDtos) {
        Optional<ForumModelo> optionalForum = forumRepositorio.findById(id);
        if (optionalForum.isPresent()) {
            ForumModelo existingForum = optionalForum.get();
            BeanUtils.copyProperties(forumDtos, existingForum);
            ForumModelo updatedForum = forumRepositorio.save(existingForum);
            return ResponseEntity.ok(updatedForum);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/Forum/{id}")
    public ResponseEntity<ForumModelo> getForumById(@PathVariable UUID id) {
        Optional<ForumModelo> optionalForum = forumRepositorio.findById(id);
        return optionalForum.map(ResponseEntity::ok)
                                     .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/Forum/{id}")
    public ResponseEntity<Void> deleteForum(@PathVariable UUID id) {
        Optional<ForumModelo> optionalForum = forumRepositorio.findById(id);
        if (optionalForum.isPresent()) {
            forumRepositorio.deleteById(id);
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }
    
    
}
