package com.example.finalproject.controller;

import com.example.finalproject.model.Authority;
import com.example.finalproject.model.Event;
import com.example.finalproject.model.Invitation;
import com.example.finalproject.model.User;
import com.example.finalproject.service.AuthorityService;
import com.example.finalproject.service.EventService;
import com.example.finalproject.service.InvitationService;
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
public class UserController {

    private final UserService userService;
    private final EventService eventService;
    private final InvitationService invitationService;
    private final AuthorityService authorityService;

    public UserController(UserService userService, EventService eventService, InvitationService invitationService, AuthorityService authorityService) {
        this.userService = userService;
        this.eventService = eventService;
        this.invitationService = invitationService;
        this.authorityService = authorityService;
    }

    //rol admin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping(path = "/user")
    public ResponseEntity<User> addUser(@RequestBody User user){
        User savedUser = userService.addNewUser(user);
        Authority authority = new Authority();
        authority.setName("ROLE_USER");
        authority.setUser(savedUser);
        authorityService.addNewAuthority(authority);

        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    //rol admin si user
    //Adminul poate vedea detaliile despre toti suerii
    //Un user poate vedea doar detaliile sale
    @PreAuthorize("hasAnyRole('ROLE_ADMIN','ROLE_USER')")
    @GetMapping(path="/user/{userId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUser(@PathVariable int userId, Authentication a) {
        User userById = userService.getUserById(userId);
        if(userById.getUsername().equals(a.getName()) || a.getAuthorities().toString().contains(("ROLE_ADMIN")))
            return new ResponseEntity<>(userById, HttpStatus.OK);
        return new ResponseEntity<> (null, HttpStatus.FORBIDDEN);

    }

    //rol admin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path = "/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAllUsers(Authentication a) {
        log.info("Hello admin, " + a.getName());
        return this.userService.getAllUsers();
    }

    //rol admin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping(path ="/user")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        User updatedUser = userService.updateUser(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    //rol admin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping(path ="/user/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }


    //rol admin
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping(path="/user/username/{username}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        User userByUsername = userService.getUserByUsername(username);
        return new ResponseEntity<>(userByUsername, HttpStatus.OK);
    }

    //eveniemntele create de userul cu userId
    //rol user
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(path="/user/{userId}/created-events", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Event> getEventsByUser(@PathVariable int userId, Authentication a) {
        User userById = userService.getUserById(userId);
        if(userById.getUsername().equals(a.getName()))
             return this.eventService.findByUserId(userId);
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have access to this resource");

    }

    //pt user, in functie de id-ul lui, sa se afiseze toate invitatiile primite
    //rol user
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(path="/user/{invitedUserId}/invitations", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Invitation> getInvitationsByInvitedUserId(@PathVariable int invitedUserId,  Authentication a) {
        User userById = userService.getUserById(invitedUserId);
        if(userById.getUsername().equals(a.getName()))
             return this.invitationService.findInvitationsByInvitedUserId(invitedUserId);
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have access to this resource");
    }


    //pt user, in functie de id-ul lui, sa se afiseze invitatiile primite (cu send)
    //rol user
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(path="/user/{userId}/received-invitations", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Invitation> getReceivedInvitationsByUserId(@PathVariable int userId, Authentication a) {
        User userById = userService.getUserById(userId);
        if(userById.getUsername().equals(a.getName()))
             return this.invitationService.findReceivedInvitationsByUserId(userId);
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have access to this resource");
    }

    //pt user, in functie de id-ul lui, sa se afiseze invitatiile acceptate (cu accept)
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(path="/user/{userId}/accepted-invitations", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Invitation> getAcceptedInvitationsByUserId(@PathVariable int userId, Authentication a) {
        User userById = userService.getUserById(userId);
        if(userById.getUsername().equals(a.getName()))
            return this.invitationService.findAcceptedInvitationsByUserId(userId);

        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have access to this resource");
    }

    //pt user, in functie de id-ul lui, sa se afiseze invitatiile refuzate (cu reject)
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(path="/user/{userId}/rejected-invitations", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Invitation> getRejectedInvitationsByUserId(@PathVariable int userId, Authentication a){

            User userById = userService.getUserById(userId);
            if (userById.getUsername().equals(a.getName())) {
                    return this.invitationService.findRejectedInvitationsByUserId(userId);

        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have access to this resource");

    }

    //userul accepta sau refuza invitatia
    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/user/{userId}/response-to-invitation/{invitationId}")
    public ResponseEntity<Invitation> acceptOrRejectInvitation (Authentication a, @RequestBody Invitation invitation, @PathVariable int userId, @PathVariable int invitationId) {
        User userById = userService.getUserById(userId);
        if(userById.getUsername().equals(a.getName())) {
            Invitation updatedInvitation = invitationService.responseToInvitation(invitation, userId, invitationId);
            return new ResponseEntity<>(updatedInvitation, HttpStatus.OK);
        }
        return new ResponseEntity<> (null, HttpStatus.FORBIDDEN);
    }

    //pt user, in functie de id-ul lui, sa se afiseze toate invitatiile la evenimentele lui (join cu Event)
    //rol user
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping(path="/user/{plannerUserId}/sent-invitations", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Invitation> getSentInvitationsByPlannerUserId(@PathVariable int plannerUserId, Authentication a) {
        User userById = userService.getUserById(plannerUserId);
        if(userById.getUsername().equals(a.getName()))
            return this.invitationService.findSendInvitationsByPlannerUserId(plannerUserId);
        throw new ResponseStatusException(HttpStatus.FORBIDDEN, "You don't have access to this resource");
    }

}
