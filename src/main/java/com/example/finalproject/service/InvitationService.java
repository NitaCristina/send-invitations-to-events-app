package com.example.finalproject.service;


import com.example.finalproject.model.Event;
import com.example.finalproject.model.Invitation;


import java.util.List;

public interface InvitationService {

    Invitation addInvitation(Invitation invitation);
    List<Invitation> getAllInvitations();
    Invitation getInvitationById(int invitationId);
    List<Invitation> findInvitationsByInvitedUserId(int invitedUserId);
    List<Invitation> findSendInvitationsByPlannerUserId(int plannerUserId);
    List<Invitation> findReceivedInvitationsByUserId(int userId);
    List<Invitation> findAcceptedInvitationsByUserId(int userId);
    List<Invitation> findRejectedInvitationsByUserId(int userId);
    Invitation responseToInvitation(Invitation invitation, int userId, int invitationId);
}
