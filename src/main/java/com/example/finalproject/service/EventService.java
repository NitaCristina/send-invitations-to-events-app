package com.example.finalproject.service;

import com.example.finalproject.model.Event;
import java.util.List;

public interface EventService {

    Event addNewEvent(Event event);
    Event updateEvent(Event event);
    Event getEventById(int eventId);
    List<Event> findByUserId(int userId);
    void deleteEvent(int eventId);
    List<Event> getAllEvents();

}
