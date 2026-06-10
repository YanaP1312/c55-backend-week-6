package net.hackyourfuture.backend.week6.postify.controllers;

import net.hackyourfuture.backend.week6.postify.client.LyricsClient;
import net.hackyourfuture.backend.week6.postify.dto.response.lyric.LyricApiResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class TrackLyricsControllerTest{

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private LyricsClient lyricsClient;

    @Test
    void getLyrics_existingTrack_returnsLyrics() throws Exception{

        when(lyricsClient.getLyrics("Billie Eilish", "LUNCH"))
                .thenReturn(new LyricApiResponse("""
                        Line 1
                        Line 2
                        """));

        mockMvc.perform(get("/tracks/41/lyrics"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.trackId").value(41))
                .andExpect(jsonPath("$.trackTitle").value("LUNCH"))
                .andExpect(jsonPath("$.artistName").value("Billie Eilish"))
                .andExpect(jsonPath("$.lyrics[0]").value("Line 1"))
                .andExpect(jsonPath("$.lyrics[1]").value("Line 2"));


    }

    @Test
    void getLyrics_nonExistingTrack_returns404() throws Exception {
        mockMvc.perform(get("/tracks/9999/lyrics"))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Track with id 9999 not found"));
    }

    @Test
    void getLyrics_existingTrackButNoLyrics_returns404() throws Exception {

        when(lyricsClient.getLyrics("Billie Eilish", "LUNCH"))
                .thenReturn(new LyricApiResponse(""));

        mockMvc.perform(get("/tracks/41/lyrics"))
                .andExpect(status().isNotFound())
                .andExpect(content().string( "Lyrics not found for track 'LUNCH' by 'Billie Eilish'"));
    }
}
