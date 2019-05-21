package com.mdi.backend.controllers;

import com.mdi.backend.models.Choice;
import com.mdi.backend.models.Poll;
import com.mdi.backend.repositories.ChoiceRepository;
import com.mdi.backend.repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
public class PollResource {

    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private ChoiceRepository choiceRepository;

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/polls")
    public List<Poll> retrieveAllPolls() {
        return pollRepository.findAll(Sort.by(Sort.Direction.ASC,"name"));
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/polls/{id}")
    public Poll retrievePoll(@PathVariable long id) {
        return isPollExisting(id);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @DeleteMapping("/polls/{id}")
    public void deletePoll(@PathVariable long id) {
        Poll poll = isPollExisting(id);
        for (Choice choice: poll.getChoices()) {
            choiceRepository.deleteById(choice.getId());
        }
        pollRepository.deleteById(id);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/polls")
    public Poll createPoll(@Valid @RequestBody Poll poll) {
        for (Choice choice:poll.getChoices()) {
            choice.setPoll(poll);
            choiceRepository.saveAndFlush(choice);
        }
        Poll savedPoll = pollRepository.saveAndFlush(poll);
        return savedPoll;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PutMapping("/students/{id}")
    public ResponseEntity<Object> updatePoll(@Valid @RequestBody Poll poll, @PathVariable long id) {

        Optional<Poll> optionalPoll = pollRepository.findById(id);

        if (!optionalPoll.isPresent())
            return ResponseEntity.notFound().build();

        Poll actualPoll = optionalPoll.get();
        actualPoll.setName(poll.getName());
        actualPoll.setLocation(poll.getLocation());
        actualPoll.setDescription(poll.getDescription());

        return ResponseEntity.noContent().build();
    }

    private Poll isPollExisting(long id){
        Optional<Poll> poll = pollRepository.findById(id);
        if (!poll.isPresent()) {
            throw new PollNotFoundException("id-" + id);
        }
        return poll.get();
    }
}
