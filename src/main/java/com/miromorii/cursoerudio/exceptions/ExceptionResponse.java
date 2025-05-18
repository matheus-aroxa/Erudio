package com.miromorii.cursoerudio.exceptions;

import java.util.Date;

public record ExceptionResponse(Date timeStamp, String message, String details) {
}
