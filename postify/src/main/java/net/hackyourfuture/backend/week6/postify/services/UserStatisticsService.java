package net.hackyourfuture.backend.week6.postify.services;

import net.hackyourfuture.backend.week6.postify.dto.response.user.UserStatisticsResponse;
import net.hackyourfuture.backend.week6.postify.exceptions.UserNotFoundException;
import net.hackyourfuture.backend.week6.postify.repository.UserStatisticsRepository;
import org.springframework.stereotype.Service;

@Service
public class UserStatisticsService {

    private final UserStatisticsRepository repository;

    public UserStatisticsService(UserStatisticsRepository repository){
        this.repository = repository;
    }

    public UserStatisticsResponse getStatisticsForUser(int userId) {
        if (!repository.userExists(userId)) {
            throw new UserNotFoundException(userId);
        }

        return repository.fetchStatistics(userId).orElseThrow(() -> new UserNotFoundException(userId));
    }
}

