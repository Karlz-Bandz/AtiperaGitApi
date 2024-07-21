package com.atipera.gitapi.exception.git;

public class GitUnauthorizedException extends RuntimeException{

    public GitUnauthorizedException(String message) {
        super(message);
    }
}
