package net.hackyourfuture.backend.week6.postify.client;

import net.hackyourfuture.backend.week6.postify.dto.response.lyric.LyricApiResponse;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClient;

@Service
public class LyricsClient {
    private final RestClient lyricsRestClient;

    public LyricsClient(@Qualifier("lyricsRestClient") RestClient lyricsRestClient){
        this.lyricsRestClient = lyricsRestClient;
    }

    public LyricApiResponse getLyrics(String artistName, String trackTitle){
        try {
            return lyricsRestClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v1/{artist}/{title}")
                            .build(artistName, trackTitle))
                    .retrieve()
                    .body(LyricApiResponse.class);
        } catch(HttpClientErrorException.NotFound ex){
            return null;
        }

    }
}
