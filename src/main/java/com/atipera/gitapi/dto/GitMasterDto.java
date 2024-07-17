package com.atipera.gitapi.dto;

import java.util.List;

public record GitMasterDto(String userName, List<GitDto> repositories) {
}
