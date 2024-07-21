package com.atipera.gitapi.dto;

import lombok.Builder;

@Builder
public record CommitDto(String sha) {
}
