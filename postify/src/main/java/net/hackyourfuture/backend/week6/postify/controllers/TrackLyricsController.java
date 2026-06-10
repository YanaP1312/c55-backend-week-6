package net.hackyourfuture.backend.week6.postify.controllers;

import net.hackyourfuture.backend.week6.postify.dto.response.lyric.TrackLyricResponse;
import net.hackyourfuture.backend.week6.postify.services.TrackLyricsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tracks")
public class TrackLyricsController {
    private final TrackLyricsService service;

    public TrackLyricsController(TrackLyricsService service){
        this.service = service;
    }

    @GetMapping("/{id}/lyrics")
    public TrackLyricResponse getLyrics(@PathVariable("id") int trackId){
        return service.getLyricsForTrack(trackId);
    }
}
