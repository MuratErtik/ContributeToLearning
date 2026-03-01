package org.example.contributetolearning.exceptions;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorDetail {


    private String error;

    private String details;

    private LocalDateTime timestamp;
}
