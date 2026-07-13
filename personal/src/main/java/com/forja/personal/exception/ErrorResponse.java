package com.forja.personal.exception;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorResponse {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss'Z'")
    private LocalDateTime timestamp;
    
    private int statusCode;
    private String status;
    private String error;
    private String message;
    private String path;
    private String transactionId;

    public ErrorResponse(HttpStatus httpStatus, String message, String path, String transactionId) {
        this.timestamp = LocalDateTime.now();
        this.statusCode = httpStatus.value();
        this.status = httpStatus.name();
        this.error = httpStatus.getReasonPhrase();
        this.message = message;
        this.path = path;
        this.transactionId = transactionId;
    }
}