package net.hackyourfuture.backend.week6.postify.dto.response.user;

public record UserStatisticsResponse(
        int userId,
        String userName,
        String userCountry,
        int totalStreams,
        int uniqueTracksStreamed,
        int uniqueArtistsStreamed,
        String favoriteGenre,
        int totalListeningTimeSeconds
        ) {
}
