package com.ninja.ninjabid.security;

import com.ninja.ninjabid.domain.User;
import com.ninja.ninjabid.repository.UserRepository;

/**
 * Utility class for Spring Security.
 */
public final class UserUtils {

    private UserUtils() {
    }


    /**
     * Get the login of the current user.
     *
     * @return the login of the current user
     */
    public static boolean checkCreditOwnership(User user) {
       return  true;
    }
}
