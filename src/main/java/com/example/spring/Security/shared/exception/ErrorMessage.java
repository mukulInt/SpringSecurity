package com.example.spring.Security.shared.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO to hold error message details.
 */
@Data
@NoArgsConstructor(force = true)
@AllArgsConstructor
public class ErrorMessage {
	private int status;
	private String errorMsg;
}

