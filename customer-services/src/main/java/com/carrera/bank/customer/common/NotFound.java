package com.carrera.bank.customer.common;

/**
 * NotFound enum
 *
 * @author acarrera
 * @version 1.0
 */
public enum NotFound {
    NOT_FOUND_CUSTOMER("Customer not found."),
    NOT_FOUND_PERSON("Person not found.");

    private final String notFoundMessage;

    NotFound(final String notFoundMessage) {
        this.notFoundMessage = notFoundMessage;
    }

    @Override
    public String toString() {
        return this.notFoundMessage;
    }
}
