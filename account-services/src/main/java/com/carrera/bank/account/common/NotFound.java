package com.carrera.bank.account.common;

/**
 * NotFound enum
 *
 * @author acarrera
 * @version 1.0
 */
public enum NotFound {
    NOT_FOUND_ACCOUNT("Account not found."),
    NOT_FOUND_TRANSACTION("Transaction not found.");

    private final String notFoundMessage;

    NotFound(final String notFoundMessage) {
        this.notFoundMessage = notFoundMessage;
    }

    @Override
    public String toString() {
        return this.notFoundMessage;
    }
}
