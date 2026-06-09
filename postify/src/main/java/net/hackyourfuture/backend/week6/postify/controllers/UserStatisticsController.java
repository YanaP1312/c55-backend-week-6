package net.hackyourfuture.backend.week6.postify.controllers;

import net.hackyourfuture.backend.week6.postify.dto.response.user.UserStatisticsResponse;
import net.hackyourfuture.backend.week6.postify.services.UserStatisticsService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserStatisticsController {

    private final UserStatisticsService service;

    public UserStatisticsController(UserStatisticsService service){
        this.service = service;
    }

    @GetMapping("/{id}/stats")
    public UserStatisticsResponse getUserStats(@PathVariable int id){
        return service.getStatisticsForUser(id);
    }
}
