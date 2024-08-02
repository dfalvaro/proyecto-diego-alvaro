package com.proyecto.neo.app.general.exceptions;

import java.util.List;

import lombok.Data;

@Data
public class ErrorResponse {
    private String message;
    private List<FieldError> fieldErrors;

    public ErrorResponse(String message, List<FieldError> fieldErrors) {
        this.message = message;
        this.fieldErrors = fieldErrors;
    }

    @Data
    public static class FieldError {
        private String field;
        private String rejectedValue;
        private String message;

        public FieldError(String field, String rejectedValue, String message) {
            this.field = field;
            this.rejectedValue = rejectedValue;
            this.message = message;
        }
    }
}
