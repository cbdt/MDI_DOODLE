package com.mdi.backend.models;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
public class Poll {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String name;
    private String location;
    private String description;

    @CreationTimestamp
    private Date createdAt;

    @UpdateTimestamp
    private Date updatedAt;

    @OneToMany(cascade=CascadeType.ALL)
    @OrderBy("name")
    List<Choice> choices;

    public Poll() {
    }

    public Poll(String name, String location, String description, List<Choice> choices) {
        this.name = name;
        this.location = location;
        this.description = description;
        this.choices=choices;
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
    public void removeChoice(Choice choice){
        this.choices.remove(choice);
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

    public void setLocation(String location) {
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
                ", location='" + location + '\'' +
                ", description='" + description + '\'' +
                '}';
    }
}
