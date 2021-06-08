package com.example.finalproject.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.ToString;

import javax.persistence.*;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

@Entity
@Data
@Table(name = "events")
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String title;
    private String address;


    private String description;

    private String date;
    private String time;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="planner")
    @ToString.Exclude
    @JsonBackReference
    private User planner;

    @OneToMany(mappedBy = "event", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
   // @JsonManagedReference
    @JsonIgnore
    @ToString.Exclude
    private List<Invitation> invitationList;


}
