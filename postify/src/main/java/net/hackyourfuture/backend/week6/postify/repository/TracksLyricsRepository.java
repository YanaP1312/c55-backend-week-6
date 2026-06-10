package net.hackyourfuture.backend.week6.postify.repository;

import net.hackyourfuture.backend.week6.postify.dto.response.lyric.TrackBasicInfo;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class TracksLyricsRepository {
    private final JdbcTemplate jdbcTemplate;

    public TracksLyricsRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public Optional<TrackBasicInfo> findTrackWithArtist(int trackId){
        String sql = """
                SELECT t.track_id, t.track_title, a.artist_name
                FROM tracks t
                JOIN albums al ON t.album_id = al.album_id
                JOIN artists a ON al.artist_id = a.artist_id
                WHERE t.track_id = ?
                """;

        return jdbcTemplate.query(sql,rs -> {
                if(rs.next()){
                    return Optional.of(new TrackBasicInfo(
                            rs.getInt("track_id"),
                            rs.getString("track_title"),
                            rs.getString("artist_name")
                    ));
                } return Optional.empty();
                }, trackId);
    }
}
