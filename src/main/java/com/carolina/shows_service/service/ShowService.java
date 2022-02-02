package com.carolina.shows_service.service;

import com.carolina.shows_service.model.Show;

import java.util.List;

public interface ShowService {


    Show getShow(Long id);
    List<Show> getAllShows();
}
