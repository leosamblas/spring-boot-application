package com.leosamblas.application.exception;

import lombok.Data;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
public class ExceptionDetails {
    protected String title;
    protected String status;
    protected String details;
    protected String timestamp;
}
