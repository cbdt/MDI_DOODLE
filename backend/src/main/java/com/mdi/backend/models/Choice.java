package com.mdi.backend.models;

import javax.persistence.*;

@Entity
public class Choice {

    @Id
    @GeneratedValue
    @Column(nullable = false, updatable = false)
    private Long id;

    @Basic(optional = false)
    @Column(nullable = false)
    private String name;

    @ManyToOne
    private Poll poll;

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

    public Poll getPoll() {
        return poll;
    }

    public void setPoll(Poll poll) {
        this.poll =  poll;
    }

    @Override
    public String toString() {
        return "Choice{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
