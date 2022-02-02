package com.carolina.shows_service.service;

import com.carolina.shows_service.model.Show;
import com.carolina.shows_service.repository.ShowRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
class ShowServiceImplTest {

    @Autowired
    private ShowService showService;

    @MockBean
    private ShowRepository showRepository;

    private Show mockShow1;
    private Show mockShow2;

    @BeforeEach
    void setUp() {
        this.mockShow1 = new Show(1L, "Dancing Queen", LocalDateTime.now(), LocalDateTime.now());
        this.mockShow2 = new Show(2L, "Beauty and the beast", LocalDateTime.now(), LocalDateTime.now());
    }
    @Test
    void getAllShows_returnsListOfShows() {
        // Given
        List<Show> mockShows = Arrays.asList(this.mockShow1, this.mockShow2);
        when(showRepository.findAll()).thenReturn(mockShows);

        // When
        List<Show> actualShows = showService.getAllShows();

        // Then
//        Assertions.assertEquals(mockShows.size(), actualShows.getSize());
//        Assertions.assertEquals(mockShows.get(0).getName(), actualShows.get(0).getName());
    }

    @Test
    void getShowById_returnsShow() {
        // Given
        Long mockShowId = this.mockShow1.getId();
        when(showRepository.findById(mockShowId)).thenReturn(Optional.of(this.mockShow1));

        // When
        Show actualShow = showService.getShow(mockShowId);

        // Then
        Assertions.assertNotNull(actualShow);
        Assertions.assertEquals(this.mockShow1.getName(), actualShow.getName());
    }

    @Test
    void getShowById_whenDoesntExist_throwsEntityNotFoundException() {
        // Given
        Long mockShowId = this.mockShow1.getId();
        when(showRepository.findById(mockShowId)).thenReturn(Optional.empty());

        // Then
        Assertions.assertThrows(EntityNotFoundException.class, () -> {
            showService.getShow(mockShowId);
        });
    }
}