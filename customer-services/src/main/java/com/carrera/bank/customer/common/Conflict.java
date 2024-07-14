package com.carrera.bank.customer.common;

/**
 * Conflict enum
 *
 * @author acarrera
 * @version 1.0
 */
public enum Conflict {
    GENERIC_PERSON_CONFLICT("There is a conflict with the person.");

    private final String conflictMessage;

    Conflict(final String conflictMessage) {
        this.conflictMessage = conflictMessage;
    }

    @Override
    public String toString() {
        return this.conflictMessage;
    }
}
