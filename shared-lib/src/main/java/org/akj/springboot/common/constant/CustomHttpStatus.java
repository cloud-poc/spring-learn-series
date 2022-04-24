package org.akj.springboot.common.constant;

import lombok.Data;

public enum CustomHttpStatus {
    TECHNICAL_EXCEPTION(409, "Technical exception"),
    BUSINESS_EXCEPTION(500, "Business exception");

    private final int status;

    private final String reasonPhrase;

    CustomHttpStatus(int statusCode, String reasonPhrase) {
        this.status = statusCode;
        this.reasonPhrase = reasonPhrase;
    }
}
