package net.hackyourfuture.backend.week6.postify;

import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.jdbc.core.JdbcTemplate;

public class DBConnectionCheck {

    private final JdbcTemplate jdbcTemplate;

    public DBConnectionCheck(JdbcTemplate jdbcTemplate){
        this.jdbcTemplate = jdbcTemplate;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void checkConnection(){
        String result = this.jdbcTemplate.queryForObject("SELECT 'connected'", String.class);
        System.out.println("Database status: " + result);
    }
}
