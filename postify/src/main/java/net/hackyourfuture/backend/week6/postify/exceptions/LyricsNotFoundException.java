package net.hackyourfuture.backend.week6.postify.exceptions;

public class LyricsNotFoundException extends RuntimeException{
    public LyricsNotFoundException(String trackTitle, String artistName){
        super("Lyrics not found for track '" + trackTitle + "' by '" + artistName + "'");
    }
}
