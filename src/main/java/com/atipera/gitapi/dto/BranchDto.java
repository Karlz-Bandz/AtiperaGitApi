package com.atipera.gitapi.dto;

import lombok.Builder;

@Builder
public record BranchDto(String name, String sha) {
}
