package com.atipera.gitapi.exception.git;

import lombok.Builder;

@Builder
public record GitErrorResponse(int status, String message) {
}
