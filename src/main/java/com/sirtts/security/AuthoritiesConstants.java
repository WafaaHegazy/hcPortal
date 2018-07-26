package com.sirtts.security;

/**
 * Constants for Spring Security authorities.
 */
public final class AuthoritiesConstants {

    public static final String ADMIN = "ROLE_ADMIN";

    public static final String USER = "ROLE_USER";

    public static final String ANONYMOUS = "ROLE_ANONYMOUS";

    public static final String DOCTOR = "ROLE_DOCTOR";

    public static final String MALE = "ROLE_MALE";

    public static final String FEMALE = "ROLE_FEMALE";

    private AuthoritiesConstants() {
    }
}
