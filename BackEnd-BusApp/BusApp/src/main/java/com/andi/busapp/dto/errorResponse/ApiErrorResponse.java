package com.andi.busapp.dto.errorResponse;

import java.time.LocalDateTime;

public record ApiErrorResponse(
        LocalDateTime timeStamp,
        int status,
        String message
)
{}
