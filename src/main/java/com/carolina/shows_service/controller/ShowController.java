package com.carolina.shows_service.controller;

import com.carolina.shows_service.model.Show;
import com.carolina.shows_service.service.ShowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
public class ShowController {

    private final ShowService showService;

    @Autowired
    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @GetMapping
    public ResponseEntity<List<Show>> getAllShows() {
        List<Show> shows = showService.getAllShows();
        return ResponseEntity.ok().body(shows);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Show> getShow(@PathVariable Long id) {
        try {
            Show show = showService.getShow(id);
            return ResponseEntity.ok().body(show);
        } catch (EntityNotFoundException entityNotFoundException) {
            return ResponseEntity.notFound().build();
        }
    }

}
