package com.saida.akart_task.error;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorMessage {

    public static final String DUPLICATE_USERNAME = "error.duplicateUsername";
    public static final String INVALID_PASSWORD = "error.invalidPassword";
    public static final String INVALID_USERNAME = "error.invalidUsername";
    public static final String USER_NOT_FOUND = "error.userNotFound";
}
