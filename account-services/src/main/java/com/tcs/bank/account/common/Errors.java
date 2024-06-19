package com.tcs.bank.account.common;

public enum Errors {
    INVALID_AMOUNT("Transaction value must be non-zero"),
    NOT_FOUND_TRANSACTION("Transaction not found.");

    private final String errorMessage;

    Errors(final String notFoundMessage) {
        this.errorMessage = notFoundMessage;
    }

    @Override
    public String toString() {
        return this.errorMessage;
    }
}
