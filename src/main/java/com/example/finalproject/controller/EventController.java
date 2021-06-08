package com.example.finalproject.controller;

import com.example.finalproject.model.Event;
import com.example.finalproject.model.User;
import com.example.finalproject.service.EventService;
import com.example.finalproject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
public class EventController {

    private final EventService eventService;
    private final UserService userService;

    public EventController(EventService eventService, UserService userService) {
        this.eventService = eventService;
        this.userService = userService;
    }


    //rol user
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(path= "/event", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> addEvent(@RequestBody Event event, Authentication a){
        User userByUsername = userService.getUserByUsername(a.getName());
        event.setPlanner(userByUsername);
        Event savedEvent = eventService.addNewEvent(event);
        return new ResponseEntity<>(savedEvent, HttpStatus.CREATED);
    }

    //rol admin
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping(path="/events", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getAllEvents(Authentication a) {
        log.info("Hello admin, " + a.getName());
        return this.eventService.getAllEvents();
    }

    //rol user si admin
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping(path="/events/{eventId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Event> getEvent(@PathVariable int eventId) {
        Event eventById = eventService.getEventById(eventId);
        return new ResponseEntity<>(eventById, HttpStatus.OK);
    }

    //rol user
    //se actualizeaza evenimentul/evenimentele doar ale userului logat
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping(path="/event")
    public ResponseEntity<Event> updateEvent(@RequestBody Event event, Authentication a) {
        User userByUsername = userService.getUserByUsername(a.getName());
        Event eventById = eventService.getEventById(event.getId());
        if(eventById.getPlanner() == userByUsername)
        {
            Event updatedEvent = eventService.updateEvent(event);
            return new ResponseEntity<>(updatedEvent, HttpStatus.OK);
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You cannot edit events which were created by another user");

    }


    //rol admin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path="/event/{eventId}")
    public ResponseEntity<Void> deleteEvent(@PathVariable int eventId) {
        eventService.deleteEvent(eventId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


}
