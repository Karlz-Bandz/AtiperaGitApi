package com.atipera.gitapi.service;

import com.atipera.gitapi.dto.GitMasterDto;
import reactor.core.publisher.Mono;

public interface GitService {

    Mono<GitMasterDto> getRepositories(String username);
}
