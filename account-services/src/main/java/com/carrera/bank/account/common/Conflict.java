package com.carrera.bank.account.common;

/**
 * Conflict enum
 *
 * @author acarrera
 * @version 1.0
 */
public enum Conflict {
    GENERIC_ACCOUNT_CONFLICT("There is a conflict with the account."),
    GENERIC_TRANSACTION_CONFLICT("There is a conflict with the transaction."),
    INVALID_AMOUNT("Invalid transaction amount"),
    INVALID_TRANSACTION("Balance not available.");

    private final String conflictMessage;

    Conflict(final String conflictMessage) {
        this.conflictMessage = conflictMessage;
    }

    @Override
    public String toString() {
        return this.conflictMessage;
    }
}
