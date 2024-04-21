package org.example.twitter.api.dto

class AuthResponse {
    private String message
    private String token
    private boolean isAdmin

    AuthResponse(String message, String token, boolean isAdmin) {
        this.message = message
        this.token = token
        this.isAdmin = isAdmin
    }

    String getMessage() {
        return message
    }

    void setMessage(String message) {
        this.message = message
    }

    String getToken() {
        return token
    }

    void setToken(String token) {
        this.token = token
    }

    boolean getIsAdmin() {
        return isAdmin
    }

    void setIsAdmin(boolean isAdmin) {
        this.isAdmin = isAdmin
    }
}