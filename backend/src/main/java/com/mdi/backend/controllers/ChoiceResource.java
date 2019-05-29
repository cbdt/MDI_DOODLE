package com.mdi.backend.controllers;

import com.mdi.backend.models.Choice;
import com.mdi.backend.models.Poll;
import com.mdi.backend.models.User;
import com.mdi.backend.repositories.ChoiceRepository;
import com.mdi.backend.repositories.PollRepository;
import com.mdi.backend.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin(origins = "http://localhost:8080")
public class ChoiceResource {

    @Autowired
    private ChoiceRepository choiceRepository;
    @Autowired
    private PollRepository pollRepository;
    @Autowired
    private UserRepository userRepository;


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

    @PostMapping("/polls/{idPoll}/vote/{idUser}")
    public ResponseEntity<?> vote(@RequestBody HashMap<String, List<Long>> choices, @PathVariable long idPoll, @PathVariable long idUser) {
        Poll poll = isPollExisting(idPoll);
        User user = isUserExisting(idUser);

        List<Long> idchoices = choices.get("choices");
        for (Long idChoice : idchoices) {
            Choice choice = isChoiceExisting(idChoice);

            isChoiceInPoll(poll, choice);

            if(user.getChoices().contains(choice)){
                return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
            }
            choice.addUser(user);
            choiceRepository.save(choice);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PostMapping("/polls/{idPoll}/choices/{idChoice}/removevote/{idUser}")
    public ResponseEntity<Object> removeVote(@PathVariable long idPoll, @PathVariable long idChoice, @PathVariable long idUser) {
        Poll poll = isPollExisting(idPoll);
        Choice choice = isChoiceExisting(idChoice);
        User user = isUserExisting(idUser);

        isChoiceInPoll(poll, choice);

        if(!user.getChoices().contains(choice)){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        choice.removeUser(user);
        choiceRepository.save(choice);
        user.removeChoice(choice);
        userRepository.save(user);

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/polls/{idPoll}/choices/{idChoice}/count")
    public ResponseEntity<?> numberOfVoteForChoice(@PathVariable long idPoll, @PathVariable long idChoice){
        // On vérifie que le poll et choix existent
        Poll poll = isPollExisting(idPoll);
        Choice choice = isChoiceExisting(idChoice);
        // On vérifie que le choix appartienne bien au poll
        isChoiceInPoll(poll, choice);
        // On compte le nombre de vote pour le choix
        return new ResponseEntity<>(choice.getUsers().size(),HttpStatus.OK);
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

    private User isUserExisting(long id){
        Optional<User> user = userRepository.findById(id);
        if (!user.isPresent()) {
            throw new UserNotFoundException("id-" + id);
        }
        return user.get();
    }

    private void isChoiceInPoll(Poll poll, Choice choice){
        if(!poll.getChoices().contains(choice)){
            throw new ChoiceNotFoundException("id-" + choice.getId());
        }
    }
}
