package com.example.finalproject.service.impl;

import com.example.finalproject.utils.SendInvitationManagementUtils;
import com.example.finalproject.exceptions.NotFoundException;
import com.example.finalproject.model.Invitation;
import com.example.finalproject.repository.InvitationRepository;
import com.example.finalproject.service.InvitationService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InvitationServiceImpl implements InvitationService {

    private final InvitationRepository invitationRepository;

    public InvitationServiceImpl(InvitationRepository invitationRepository) {
        this.invitationRepository = invitationRepository;
    }


    @Override
    public Invitation addInvitation(Invitation invitation) {
        return invitationRepository.save(invitation);
    }

    @Override
    public List<Invitation> getAllInvitations() {
        return this.invitationRepository.findAll();
    }

    @Override
    public Invitation getInvitationById(int invitationId) {
        return invitationRepository.findById(invitationId)
                .orElseThrow(() -> new NotFoundException("Invitation not found"));
    }

    @Override
    public List<Invitation> findInvitationsByInvitedUserId(int invitedUserId) {
        return this.invitationRepository.findInvitationsByInvitedUserId(invitedUserId);
    }

    @Override
    public List<Invitation> findSendInvitationsByPlannerUserId(int plannerUserId) {
        return this.invitationRepository.findSendInvitationsByPlannerUserId(plannerUserId);
    }

    @Override
    public List<Invitation> findReceivedInvitationsByUserId(int userId) {
        return this.invitationRepository.findReceivedInvitationsByUserId(userId);
    }

    @Override
    public List<Invitation> findAcceptedInvitationsByUserId(int userId) {
        return this.invitationRepository.findAcceptedInvitationsByUserId(userId);
    }

    @Override
    public List<Invitation> findRejectedInvitationsByUserId(int userId) {
        return this.invitationRepository.findRejectedInvitationsByUserId(userId);
    }

    @Override
    public Invitation responseToInvitation(Invitation invitation, int userId, int invitationId) {
        Invitation savedInvitation = invitationRepository.findInvitationByInvitationIdAndUserId(userId, invitationId);

        if(SendInvitationManagementUtils.checkIfInvitationStatusIsValid(invitation) == true) {
            savedInvitation.setStatus(invitation.getStatus());
            return invitationRepository.save(savedInvitation);
        }
        return savedInvitation;

    }
}
