package com.atipera.gitapi.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record GitDto(String repoName, List<BranchDto> branches) {
}
