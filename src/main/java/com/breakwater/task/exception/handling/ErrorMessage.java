package com.breakwater.task.exception.handling;

import lombok.Builder;
import lombok.Getter;

import java.time.Instant;

@Builder
@Getter
public class ErrorMessage {
    private String message;
    private Instant time;
}
