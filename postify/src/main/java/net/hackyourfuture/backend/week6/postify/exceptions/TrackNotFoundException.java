package net.hackyourfuture.backend.week6.postify.exceptions;

public class TrackNotFoundException extends RuntimeException{
    public TrackNotFoundException(int id){
        super("Track with id " + id + " not found");
    }
}
