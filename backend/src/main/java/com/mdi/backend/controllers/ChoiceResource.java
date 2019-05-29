package com.mdi.backend.controllers;

import com.mdi.backend.models.Choice;
import com.mdi.backend.models.Poll;
import com.mdi.backend.repositories.ChoiceRepository;
import com.mdi.backend.repositories.PollRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class ChoiceResource {

    @Autowired
    private ChoiceRepository choiceRepository;
    @Autowired
    private PollRepository pollRepository;


    @GetMapping("/polls/{idPoll}/choices")
    public ResponseEntity<List<Choice>> retrieveAllChoicesFromPoll(@PathVariable long idPoll) {
        return new ResponseEntity<>(isPollExisting(idPoll).getChoices(), HttpStatus.OK);
    }

    @GetMapping("/polls/{idPoll}/choices/{idChoice}")
    public ResponseEntity<Choice> retrieveChoiceFromPoll(@PathVariable long idPoll, @PathVariable long idChoice) {
        Poll poll = isPollExisting(idPoll);
        Choice choice = isChoiceExisting(idChoice);

        isChoiceInPoll(poll, choice);

        return new ResponseEntity<>(choice, HttpStatus.OK);
    }

    @DeleteMapping("/polls/{idPoll}/choices/{idChoice}")
    public ResponseEntity<?> deleteChoiceFromPoll(@PathVariable long idPoll, @PathVariable long idChoice) {
        Poll poll = isPollExisting(idPoll);
        Choice choice = isChoiceExisting(idChoice);

        isChoiceInPoll(poll, choice);

        poll.removeChoice(choice);
        pollRepository.save(poll);

        choiceRepository.deleteById(idChoice);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/polls/{idPoll}/choices")
    public ResponseEntity<List<Choice>> createChoices(@Valid @RequestBody List<Choice> choices, @PathVariable long idPoll) {
        Poll poll = isPollExisting(idPoll);
        for (Choice choice:choices) {
            poll.addChoice(choice);
            pollRepository.save(poll);
        }
        return new ResponseEntity<>(choices, HttpStatus.CREATED);
    }

    @PutMapping("/polls/{idPoll}/choices/{idChoice}")
    public ResponseEntity<Object> updateChoice(@Valid @RequestBody Choice newChoice, @PathVariable long idPoll, @PathVariable long idChoice) {

        Poll poll = isPollExisting(idPoll);
        Choice choice = isChoiceExisting(idChoice);

        isChoiceInPoll(poll, choice);

        if (newChoice.getName()!=null){
            choice.setName(choice.getName());
        }

        Choice updatedChoice = choiceRepository.save(choice);
        return new ResponseEntity<>(updatedChoice, HttpStatus.OK);
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

    private void isChoiceInPoll(Poll poll, Choice choice){
        if(!poll.getChoices().contains(choice)){
            throw new ChoiceNotFoundException("id-" + choice.getId());
        }
    }
}
