package com.mdi.backend.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Poll {

    @Id
    @GeneratedValue
    private Long id;
    private String name;
    private String localion;
    private String description;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.PERSIST)
    List<Choice> choices;

    public Poll() {
    }

    public Poll(String name, String localion, String description) {
        this.name = name;
        this.localion = localion;
        this.description = description;
        choices = new ArrayList<Choice>();
    }

    public void addChoice(Choice choice){
        this.choices.add(choice);
    }

    public void setId(Long id){
        this.id=id;
    }
    public Long getId() {
        return id;
    }

    public List<Choice> getChoices() {
        return choices;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocalion() {
        return localion;
    }

    public void setLocalion(String localion) {
        this.localion = localion;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Poll{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", localion='" + localion + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
