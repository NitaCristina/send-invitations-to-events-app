package com.example.finalproject.utils;

import com.example.finalproject.exceptions.InvalidInvitationStatusException;
import com.example.finalproject.model.Invitation;

public class SendInvitationManagementUtils {

    SendInvitationManagementUtils(){

    }

    public static boolean checkIfInvitationStatusIsValid(final Invitation invitation)
    {
        if(invitation.getStatus().equals("accepted") || invitation.getStatus().equals("rejected"))
            return true;
        else {
            throw new InvalidInvitationStatusException("Invalid response to invitation. It should be either accepted or rejected.");
        }

    }
}
