package com.mdi.backend.controllers;

import com.mdi.backend.models.Poll;
import com.mdi.backend.repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.Optional;

@RestController
public class PollResource {

    @Autowired
    private PollRepository pollRepository;

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/polls")
    public List<Poll> retrieveAllPolls() {
        return pollRepository.findAll();
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/polls/{id}")
    public Poll retrievePoll(@PathVariable long id) {
        Optional<Poll> poll = pollRepository.findById(id);

        if (!poll.isPresent())
            throw new PollNotFoundException("id-" + id);

        return poll.get();
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @DeleteMapping("/polls/{id}")
    public void deletePoll(@PathVariable long id) {
        pollRepository.deleteById(id);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/polls")
    public Poll createPoll(@RequestBody Poll poll) {
        Poll savedPoll = pollRepository.save(poll);
        return savedPoll;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PutMapping("/students/{id}")
    public ResponseEntity<Object> updatePoll(@RequestBody Poll poll, @PathVariable long id) {

        Optional<Poll> pollOptional = pollRepository.findById(id);

        if (!pollOptional.isPresent())
            return ResponseEntity.notFound().build();

        poll.setId(id);

        pollRepository.save(poll);

        return ResponseEntity.noContent().build();
    }
}
