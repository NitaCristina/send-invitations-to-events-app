package com.example.finalproject.util;


import com.example.finalproject.utils.SendInvitationManagementUtils;
import com.example.finalproject.exceptions.InvalidInvitationStatusException;
import com.example.finalproject.model.Invitation;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class SendInvitationManagementUtilTests {

    @Test
    @DisplayName("Should return true if invitation status is accepted")
    void ifInvitationHasStatusAccepted(){
        Invitation invitation = new Invitation();
        invitation.setStatus("accepted");

        Boolean isStatusAccepted = SendInvitationManagementUtils.checkIfInvitationStatusIsValid(invitation);

        assertEquals(true, isStatusAccepted);
    }

    @Test
    @DisplayName("Should return true if invitation status is rejected")
    void ifInvitationHasStatusRejected(){
        Invitation invitation = new Invitation();
        invitation.setStatus("rejected");

        Boolean isStatusRejected = SendInvitationManagementUtils.checkIfInvitationStatusIsValid(invitation);

        assertEquals(true, isStatusRejected);
    }


    @Test
    void shouldThrowInvalidInvitationStatusExceptionIfInvitationStatusIsNeitherAcceptedNorRejected() {
        Invitation invitation = new Invitation();
        invitation.setStatus("unsure");

        assertThrows(InvalidInvitationStatusException.class,
                () -> SendInvitationManagementUtils.checkIfInvitationStatusIsValid(invitation), "Invalid response to invitation. It should be either accepted or rejected.");
    }

}
