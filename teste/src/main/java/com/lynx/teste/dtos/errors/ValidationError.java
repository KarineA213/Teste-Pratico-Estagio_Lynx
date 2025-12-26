package com.lynx.teste.dtos.errors;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

public class ValidationError {

    private List<FieldMessage> error = new ArrayList<>();

    public ValidationError(Instant timestamp, String fieldName, String message) {
    }

    public void addError(String fieldName, String message) {
        error.add(new FieldMessage(fieldName, message));
    }
}
