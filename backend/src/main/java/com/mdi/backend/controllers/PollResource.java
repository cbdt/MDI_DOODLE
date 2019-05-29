package com.mdi.backend.controllers;

import com.mdi.backend.models.Poll;
import com.mdi.backend.repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class PollResource {

    @Autowired
    private PollRepository pollRepository;

    @GetMapping("/polls")
    public ResponseEntity<List<Poll>> retrieveAllPolls() {
        return new ResponseEntity<>(pollRepository.findAll(Sort.by(Sort.Direction.ASC,"name")), HttpStatus.OK );
    }

    @GetMapping("/polls/{id}")
    public ResponseEntity<Poll> retrievePoll(@PathVariable long id) {
        return new ResponseEntity<>(isPollExisting(id), HttpStatus.OK);
    }

    @DeleteMapping("/polls/{id}")
    public ResponseEntity<?> deletePoll(@PathVariable long id) {
        Poll poll = isPollExisting(id);
        // Les choices sont delete part le cascade type all;
        pollRepository.deleteById(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/polls")
    public ResponseEntity<Poll> createPoll(@Valid @RequestBody Poll poll) {
        Poll savedPoll = pollRepository.save(poll);
        return new ResponseEntity<>(savedPoll, HttpStatus.CREATED);
    }

    @PutMapping("/students/{id}")
    public ResponseEntity<Poll> updatePoll(@Valid @RequestBody Poll newPoll, @PathVariable long id) {
        Poll poll = isPollExisting(id);

        // On met à jour l'ancien poll
        if (newPoll.getName()!=null){
            poll.setName(newPoll.getName());
        }
        if (newPoll.getLocation()!=null){
            poll.setLocation(newPoll.getLocation());
        }
        if (newPoll.getDescription()!=null){
            poll.setDescription(newPoll.getDescription());
        }

        Poll updatedPoll = pollRepository.save(poll);
        return new ResponseEntity<>(updatedPoll, HttpStatus.OK);
    }

    private Poll isPollExisting(long id){
        Optional<Poll> poll = pollRepository.findById(id);
        if (!poll.isPresent()) {
            throw new PollNotFoundException("id-" + id);
        }
        return poll.get();
    }
}
