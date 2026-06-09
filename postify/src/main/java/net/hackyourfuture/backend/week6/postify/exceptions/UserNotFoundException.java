package net.hackyourfuture.backend.week6.postify.exceptions;

public class UserNotFoundException extends RuntimeException{
    public UserNotFoundException(int id){
        super("User with id " + id + " not found");
    }
}
