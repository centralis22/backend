package edu.usc.marshall.centralis22.service;

import org.springframework.stereotype.Service;

/**
 * Validates if a user is an admin/instructor,
 * or adds a new team if it does not exist.
 */
@Service
public class UserManagement {

    /**
     * Validates
     * @return
     */
    public boolean validateAdmin() {
        return true;
    }
}
