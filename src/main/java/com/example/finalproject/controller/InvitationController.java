package com.example.finalproject.controller;

import com.example.finalproject.model.Event;
import com.example.finalproject.model.Invitation;
import com.example.finalproject.model.User;
import com.example.finalproject.service.EventService;
import com.example.finalproject.service.InvitationService;
import com.example.finalproject.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
public class InvitationController {

    private final EventService eventService;
    private final InvitationService invitationService;
    private final UserService userService;

    public InvitationController(EventService eventService, InvitationService invitationService, UserService userService) {
        this.eventService = eventService;
        this.invitationService = invitationService;
        this.userService = userService;
    }

    //rol user
    //trimitere invitatie catre useri la eveniment
    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping(path="/invitation/{eventId}/{userIds}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Invitation> addInvitation(@PathVariable int eventId, @PathVariable int[] userIds) {

        log.info("logged user");
        for (int userId : userIds) {
            Invitation savedInvitation = new Invitation();
            Event eventById = eventService.getEventById(eventId);
            savedInvitation.setEvent(eventById);

            User userById = userService.getUserById(userId);
            savedInvitation.setInvitedUser(userById);
            savedInvitation.setStatus("send");
            invitationService.addInvitation(savedInvitation);

        }
        return new ResponseEntity<>(HttpStatus.CREATED);

    }

    //pt admin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path ="/invitations", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Invitation> getAllInvitations(Authentication a) {
        log.info("Hello admin, " + a.getName());
        return this.invitationService.getAllInvitations();
    }

    //pt admin cu id invitation
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path ="/invitations/{invitationId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Invitation> getInvitation(@PathVariable int invitationId) {
        Invitation invitationById = invitationService.getInvitationById(invitationId);
        return new ResponseEntity<>(invitationById, HttpStatus.OK);
    }


}
