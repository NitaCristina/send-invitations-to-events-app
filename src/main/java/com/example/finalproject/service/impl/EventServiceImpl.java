package com.example.finalproject.service.impl;

import com.example.finalproject.exceptions.NotFoundException;
import com.example.finalproject.model.Event;
import com.example.finalproject.repository.EventRepository;
import com.example.finalproject.service.EventService;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class EventServiceImpl implements EventService {

    private final EventRepository eventRepository;

    public EventServiceImpl(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }


    @Override
    public Event addNewEvent(Event event) {
        return eventRepository.save(event);
    }

    @Override
    public Event updateEvent(Event event) {
        Event savedEvent = getEventById(event.getId());
        savedEvent.setTitle(Optional.ofNullable(event.getTitle()).orElse(savedEvent.getTitle()));
        savedEvent.setAddress(Optional.ofNullable(event.getAddress()).orElse(savedEvent.getAddress()));
        savedEvent.setDescription(Optional.ofNullable(event.getDescription()).orElse(savedEvent.getDescription()));
        savedEvent.setDate(Optional.ofNullable(event.getDate()).orElse(savedEvent.getDate()));
        savedEvent.setTime(Optional.ofNullable(event.getTime()).orElse(savedEvent.getTime()));
        savedEvent.setPlanner(Optional.ofNullable(event.getPlanner()).orElse(savedEvent.getPlanner()));

        return eventRepository.save(savedEvent);
    }

    @Override
    public Event getEventById(int eventId) {
        return eventRepository.findById(eventId)
                .orElseThrow(() -> new NotFoundException("Event not found"));
    }

    @Override
    public List<Event> findByUserId(int userId) {
        return eventRepository.findByUserId(userId);
    }

    @Override
    public List<Event> getAllEvents()
    {
        return this.eventRepository.findAll();
    }


    @Override
    public void deleteEvent(int eventId) {

        Event currentEvent = getEventById(eventId);
        eventRepository.delete(currentEvent);

    }

}
