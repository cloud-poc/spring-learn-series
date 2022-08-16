package org.akj.springboot.authorization.exception;

import lombok.Data;

public enum ErrorCodeMap {
    INVALID_DATA_STAT("100-00", "Invalid data state."),
    USER_NOT_EXIST("100-01", "User does not exist."),
    GROUP_CANNOT_BE_FOUND("100-02", "No group was found.");

    private String code;
    private String message;

    ErrorCodeMap(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String code() {
        return this.code;
    }

    public String message() {
        return this.message;
    }

}
