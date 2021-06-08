package com.example.finalproject.repository;

import com.example.finalproject.model.Event;
import com.example.finalproject.model.Invitation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface InvitationRepository extends JpaRepository<Invitation, Integer> {

    @Query("Select i From Invitation i where i.invitedUser.id=:invitedUserId")
    List<Invitation> findInvitationsByInvitedUserId(int invitedUserId);

    @Query("Select i From Invitation i join i.event where i.event.planner.id=:plannerUserId")
    List<Invitation> findSendInvitationsByPlannerUserId(int plannerUserId);

    @Query("Select i From Invitation i where i.invitedUser.id=:userId and i.status= 'send'")
    List<Invitation> findReceivedInvitationsByUserId(int userId);

    @Query("Select i From Invitation i where i.invitedUser.id=:userId and i.status= 'accepted'")
    List<Invitation> findAcceptedInvitationsByUserId(int userId);

    @Query("Select i From Invitation i where i.invitedUser.id=:userId and i.status= 'rejected'")
    List<Invitation> findRejectedInvitationsByUserId(int userId);

    @Query("Select i From Invitation i where i.id=:invitationId and i.invitedUser.id=:userId")
    Invitation findInvitationByInvitationIdAndUserId(int userId, int invitationId);
}
