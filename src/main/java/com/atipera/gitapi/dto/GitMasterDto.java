package com.atipera.gitapi.dto;

import lombok.Builder;

import java.util.List;

@Builder
public record GitMasterDto(String userName, List<GitDto> repositories) {
}
