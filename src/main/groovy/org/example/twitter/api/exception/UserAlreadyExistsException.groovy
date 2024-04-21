package org.example.twitter.api.exception

class UserAlreadyExistsException extends RuntimeException {

    UserAlreadyExistsException(String message) {
        super(message)
    }
}
