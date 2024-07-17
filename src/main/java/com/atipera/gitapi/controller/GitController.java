package com.atipera.gitapi.controller;

import com.atipera.gitapi.dto.GitMasterDto;
import com.atipera.gitapi.service.GitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequiredArgsConstructor
@RequestMapping("/git")
public class GitController {

    private final GitService gitService;

    @GetMapping("/repos/{username}")
    Mono<ResponseEntity<GitMasterDto>> getRepositories(@PathVariable("username") String username) {
        return gitService.getRepositories(username)
                .map(value -> ResponseEntity.ok().body(value))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }
}
