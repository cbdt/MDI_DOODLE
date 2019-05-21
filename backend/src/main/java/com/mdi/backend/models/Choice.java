package com.mdi.backend.models;

import javax.persistence.*;

@Entity
public class Choice {

    @Id
    @GeneratedValue
    private Long id;

    private String name;

    public Choice (){
    }

    public Choice(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
