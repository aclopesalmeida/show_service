package com.carolina.shows_service.service;

import com.carolina.shows_service.model.Show;
import com.carolina.shows_service.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
public class ShowServiceImpl implements ShowService {

    private final ShowRepository showRepository;

    @Autowired
    public ShowServiceImpl(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Override
    public Show getShow(Long id) {
        Optional<Show> optionalShow = this.showRepository.findById(id);
        if (!optionalShow.isPresent()) {
            throw new EntityNotFoundException();
        }
        return optionalShow.get();
    }

    @Override
    public List<Show> getAllShows() {
        return this.showRepository.findAll();
    }
}
