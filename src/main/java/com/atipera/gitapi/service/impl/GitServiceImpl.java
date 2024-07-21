package com.atipera.gitapi.service.impl;

import com.atipera.gitapi.dto.BranchDto;
import com.atipera.gitapi.dto.GitDto;
import com.atipera.gitapi.dto.GitMasterDto;
import com.atipera.gitapi.dto.RepoDto;
import com.atipera.gitapi.exception.git.GitNotFoundException;
import com.atipera.gitapi.exception.git.GitUnauthorizedException;
import com.atipera.gitapi.service.GitService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.ClientResponse;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class GitServiceImpl implements GitService {

    private static final String GIT_API_URL = "https://api.github.com";

    private final WebClient webClient;

    @Override
    public Mono<GitMasterDto> getRepositories(String username) {
        String apiUrl = GIT_API_URL + "/users/" + username + "/repos";

            return webClient.get()
                    .uri(apiUrl)
                    .retrieve()
                    .onStatus(HttpStatusCode::is4xxClientError, this::handle4xxError)
                    .bodyToFlux(RepoDto.class)
                    .flatMap(repoDto ->
                            getBranchesForRepository(username, repoDto.name())
                                    .collectList()
                                    .map(branches ->
                                            GitDto.builder()
                                                    .repoName(repoDto.name())
                                                    .branches(branches)
                                                    .build()))
                    .collectList()
                    .map(repos ->
                            GitMasterDto.builder()
                                    .userName(username)
                                    .repositories(repos)
                                    .build());
    }

    private Flux<BranchDto> getBranchesForRepository(String userName, String repoName) {
        String apiUrl = GIT_API_URL + "/repos/" + userName + "/" + repoName + "/branches";
        return webClient.get()
                .uri(apiUrl)
                .retrieve()
                .bodyToFlux(BranchDto.class);
    }

    private Mono<? extends Throwable> handle4xxError(ClientResponse response) {
        if(response.statusCode() == HttpStatus.NOT_FOUND) {
            return response.bodyToMono(String.class)
                    .flatMap(errorMessage -> Mono.error(new GitNotFoundException("Git user not found!")));
        }else if(response.statusCode() == HttpStatus.UNAUTHORIZED) {
            return response.bodyToMono(String.class)
                    .flatMap(errorMessage -> Mono.error(new GitUnauthorizedException("You are unauthorized!")));
        }

        return response.createException().flatMap(Mono::error);
    }
}
