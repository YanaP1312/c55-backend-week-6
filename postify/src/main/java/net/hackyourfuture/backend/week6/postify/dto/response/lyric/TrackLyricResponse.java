package net.hackyourfuture.backend.week6.postify.dto.response.lyric;

import java.util.List;

public record TrackLyricResponse (int trackId, String trackTitle, String artistName, List<String> lyrics){
}
