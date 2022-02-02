package com.carolina.shows_service.controller;

import com.carolina.shows_service.model.Show;
import com.carolina.shows_service.service.ShowService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@WebMvcTest(ShowController.class)
class ShowControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper mapper;

    @MockBean
    private ShowService showService;

    private final String baseUrl = "http://localhost:";
    private final String port = String.valueOf(8080);
    private String apiUrl;
    private Show mockShow1;
    private Show mockShow2;

    @BeforeEach
    void setUp() {
        this.apiUrl = this.baseUrl + this.port;
        this.mockShow1 = new Show(1L, "Dancing Queen", LocalDateTime.now(), LocalDateTime.now());
        this.mockShow2 = new Show(2L, "Beauty and the beast", LocalDateTime.now(), LocalDateTime.now());
    }

    @Test
    void getAllShows_returns200WithListOfShows() throws Exception {
        // Given
        List<Show> mockShows = Arrays.asList(this.mockShow1, this.mockShow2);

        when(showService.getAllShows()).thenReturn(mockShows);

        // When
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(this.apiUrl)
                                                .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        List<Show> actualShows = this.mapper.readValue(response.getContentAsString(), List.class);

        // Then
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assertions.assertEquals(mockShows.size(), actualShows.size());
    }

    @Test
    void getShow_returns200WithShow() throws Exception {
        // Given
        Long showId = this.mockShow2.getId();
        String url = this.apiUrl + '/' + showId;
        String expectedShowName = this.mockShow1.getName();

        when(showService.getShow(showId)).thenReturn(this.mockShow1);

        // When
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
                                            .contentType(MediaType.APPLICATION_JSON)
                                            .accept(MediaType.APPLICATION_JSON);
        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();
        Show actualShow = this.mapper.readValue(response.getContentAsString(), Show.class);

        // Then
        Assertions.assertEquals(HttpStatus.OK.value(), response.getStatus());
        Assertions.assertNotNull(actualShow);
        assertEquals(expectedShowName, actualShow.getName());
    }

    @Test
    void getShow_whenShowDoesntExist_catchesExceptionAndReturns404() throws Exception {
        // GiveN
        Long showId = this.mockShow1.getId();
        String url = this.apiUrl + '/' + showId;
        when(showService.getShow(showId)).thenThrow(EntityNotFoundException.class);

        // When
        RequestBuilder requestBuilder = MockMvcRequestBuilders.get(url)
                                    .accept(MediaType.APPLICATION_JSON)
                                    .contentType(MediaType.APPLICATION_JSON);

        MvcResult result = mockMvc.perform(requestBuilder).andReturn();
        MockHttpServletResponse response = result.getResponse();

        // Then
        Assertions.assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatus());
    }
}