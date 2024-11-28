package com.devweb2.project.tasks.exceptions;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "Exception")
@JsonPropertyOrder({ "message" })
public record ExceptionResponse(
    @JsonProperty("message")
    String message
) {}
