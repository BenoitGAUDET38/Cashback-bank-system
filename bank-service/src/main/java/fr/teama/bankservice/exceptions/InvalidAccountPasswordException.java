package fr.teama.bankservice.exceptions;

public class InvalidAccountPasswordException extends Exception {
    String email;

    public InvalidAccountPasswordException() {
    }

    public InvalidAccountPasswordException(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
