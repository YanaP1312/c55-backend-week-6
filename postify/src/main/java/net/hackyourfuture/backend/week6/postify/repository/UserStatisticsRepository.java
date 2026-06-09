package net.hackyourfuture.backend.week6.postify.repository;

import net.hackyourfuture.backend.week6.postify.dto.response.user.UserBasicInfoResponse;
import net.hackyourfuture.backend.week6.postify.dto.response.user.UserStatisticsResponse;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserStatisticsRepository {

    private final JdbcTemplate jdbcTemplate;

    public UserStatisticsRepository(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    public boolean userExists(int userId){
        String sql = """
                SELECT COUNT(*)
                FROM users
                WHERE user_id = ?
                """;

        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, userId);
        return count !=null && count>0;
    }

    public Optional<UserBasicInfoResponse> fetchUserBasic(int userId){
        String sql = """
                SELECT user_id, user_name, user_country
                FROM users
                WHERE user_id = ?
                """;

        return jdbcTemplate.query(sql, rs -> {
            if(rs.next()){
                return Optional.of(new UserBasicInfoResponse(
                        rs.getInt("user_id"),
                        rs.getString("user_name"),
                        rs.getString("user_country")
                ));
            }
               return Optional.empty();
            }, userId);
    }

    public int fetchTotalStreams(int userId){
        String sql = """
                SELECT COUNT(*)
                FROM streams
                WHERE user_id = ?
                """;
        return jdbcTemplate.queryForObject(sql, Integer.class , userId);
    }

    private int fetchUniqueTracks(int userId){
        String sql = """
                SELECT COUNT(DISTINCT track_id)
                FROM streams
                WHERE user_id = ?
                """;

        return jdbcTemplate.queryForObject(sql, Integer.class, userId);
    }

    private int fetchUniqueArtists(int userId){
        String sql = """
                SELECT COUNT(DISTINCT a.artist_id)
                FROM streams s
                JOIN tracks t ON s.track_id = t.track_id
                JOIN albums al ON t.album_id = al.album_id
                JOIN artists a ON al.artist_id = a.artist_id
                WHERE s.user_id = ?
                """;
        return jdbcTemplate.queryForObject(sql, Integer.class, userId);
    }

    private String fetchFavoriteGenre(int userId){
        String sql = """
                SELECT t.genre
                FROM streams s
                JOIN tracks t ON s.track_id = t.track_id
                WHERE user_id = ?
                GROUP BY t.genre
                ORDER BY COUNT(*) DESC
                LIMIT 1
                """;
        return jdbcTemplate.query(sql,
                rs -> rs.next() ? rs.getString("genre") : null,
                userId);
    }

    private int fetchTotalListeningTime(int userId){
        String sql = """
                SELECT COALESCE(SUM(t.track_duration_s), 0)
                FROM streams s
                JOIN tracks t ON s.track_id = t.track_id
                WHERE s.user_id = ?
                """;
        return jdbcTemplate.queryForObject(sql, Integer.class, userId);
    }

    public Optional<UserStatisticsResponse> fetchStatistics(int userId){

        Optional<UserBasicInfoResponse> basic = fetchUserBasic(userId);

        if(basic.isEmpty()) return Optional.empty();

        int totalStreams = fetchTotalStreams(userId);
        int uniqueTracks = fetchUniqueTracks(userId);
        int uniqueArtists = fetchUniqueArtists(userId);
        String favoriteGenre = fetchFavoriteGenre(userId);
        int totalListeningTime = fetchTotalListeningTime(userId);

        UserBasicInfoResponse user = basic.get();

        return Optional.of(new UserStatisticsResponse(
                user.id(),
                user.name(),
                user.country(),
                totalStreams,
                uniqueTracks,
                uniqueArtists,
                favoriteGenre,
                totalListeningTime
                ));
    }
}
