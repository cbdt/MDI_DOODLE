package com.mdi.backend.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(nullable = false, updatable = false)
    private Long id;

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @Basic(optional = false)
    @Column(nullable = false)
    private String location;

    @Basic(optional = false)
    @Column(nullable = false)
    private String description;

    @OneToMany(mappedBy = "poll", cascade = CascadeType.PERSIST)
    List<Choice> choices;

    public Poll() {
    }

    public Poll(String name, String location, String description) {
        this.name = name;
        this.location = location;
        this.description = description;
        choices = new ArrayList<Choice>();
    }

    public Poll(String name, String location, String description, ArrayList<Choice> choices){
        this.name = name;
        this.location = location;
        this.description = description;
        this.choices = choices;
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

    public String getLocation() {
        return location;
    }

    public void setLocation(String localion) {
        this.location = location;
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
                ", localion='" + location + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
