package net.hackyourfuture.backend.week6.postify.services;

import net.hackyourfuture.backend.week6.postify.client.LyricsClient;
import net.hackyourfuture.backend.week6.postify.dto.response.lyric.LyricApiResponse;
import net.hackyourfuture.backend.week6.postify.dto.response.lyric.TrackBasicInfo;
import net.hackyourfuture.backend.week6.postify.dto.response.lyric.TrackLyricResponse;
import net.hackyourfuture.backend.week6.postify.exceptions.LyricsNotFoundException;
import net.hackyourfuture.backend.week6.postify.exceptions.TrackNotFoundException;
import net.hackyourfuture.backend.week6.postify.repository.TracksLyricsRepository;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class TrackLyricsService {
    private final TracksLyricsRepository repository;
    private final LyricsClient lyricsClient;

    public TrackLyricsService(TracksLyricsRepository repository, LyricsClient lyricsClient){
        this.repository = repository;
        this.lyricsClient = lyricsClient;
    }

    public TrackLyricResponse getLyricsForTrack(int trackId){

        TrackBasicInfo track = repository.findTrackWithArtist(trackId)
                .orElseThrow(() -> new TrackNotFoundException(trackId));

        LyricApiResponse apiResponse = lyricsClient.getLyrics(track.artistName(), track.trackTitle());

        if (apiResponse == null || apiResponse.lyrics() == null || apiResponse.lyrics().isBlank()){
            throw new LyricsNotFoundException(track.trackTitle(), track.artistName());
        }

        List<String> lines = Arrays.asList(apiResponse.lyrics().split("\n"));

        return new TrackLyricResponse(
              track.trackId(),
              track.trackTitle(),
              track.artistName(),
              lines
        );
    }
}
