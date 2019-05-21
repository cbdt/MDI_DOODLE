package com.mdi.backend.controllers;

import com.mdi.backend.models.Choice;
import com.mdi.backend.models.Poll;
import com.mdi.backend.repositories.ChoiceRepository;
import com.mdi.backend.repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
public class ChoiceResource {

    @Autowired
    private ChoiceRepository choiceRepository;
    @Autowired
    private PollRepository pollRepository;

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/polls/{idPoll}/choices")
    public List<Choice> retrieveAllChoicesFromPoll(@PathVariable long idPoll) {
        return isPollExisting(idPoll).getChoices();
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @GetMapping("/polls/{idPoll}/choices/{idChoice}")
    public Choice retrieveChoiceFromPoll(@PathVariable long idPoll, @PathVariable long idChoice) {
        Poll poll = isPollExisting(idPoll);
        Choice choice = isChoiceExisting(idChoice);

        List<Choice> choices = poll.getChoices();
        if(!choices.contains(choice)){
            throw new ChoiceNotFoundException("id-"+idChoice);
        }
        return choice;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @DeleteMapping("/polls/{idPoll}/choices/{idChoice}")
    public void deleteChoiceFromPoll(@PathVariable long idPoll, @PathVariable long idChoice) {
        Poll poll = isPollExisting(idPoll);
        Choice choice = isChoiceExisting(idChoice);

        List<Choice> choices = poll.getChoices();
        if(!choices.contains(choice)){
            throw new ChoiceNotFoundException("id-"+idChoice);
        }
        choiceRepository.deleteById(idChoice);
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PostMapping("/polls/{id}/choices")
    public List<Choice> createChoices(@RequestBody List<Choice> choices, @PathVariable long id) {
        Poll poll = isPollExisting(id);
        for (Choice choice:choices) {
            choice.setPoll(poll);
            poll.addChoice(choice);
            choiceRepository.save(choice);
        }
        return choices;
    }

    @CrossOrigin(origins = "http://localhost:8080")
    @PutMapping("/polls/{idPoll}/choices/{idChoice}")
    public ResponseEntity<Object> updateChoice(@RequestBody Choice choice, @PathVariable long idPoll, @PathVariable long idChoice) {

        Optional<Poll> pollOptional = pollRepository.findById(idPoll);
        Optional<Choice> choiceOptional = choiceRepository.findById(idChoice);

        if (!pollOptional.isPresent() || !choiceOptional.isPresent()) {
            return ResponseEntity.notFound().build();
        }

        choice.setId(idChoice);

        choiceRepository.save(choice);

        return ResponseEntity.noContent().build();
    }

    private Poll isPollExisting(long id){
        Optional<Poll> poll = pollRepository.findById(id);
        if (!poll.isPresent()) {
            throw new PollNotFoundException("id-" + id);
        }
        return poll.get();
    }

    private Choice isChoiceExisting(long id){
        Optional<Choice> choice = choiceRepository.findById(id);
        if (!choice.isPresent()) {
            throw new ChoiceNotFoundException("id-" + id);
        }
        return choice.get();
    }
}
