package com.carolina.shows_service.seeder;

import com.carolina.shows_service.model.Show;
import com.carolina.shows_service.repository.ShowRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
@ConditionalOnProperty(value ="loadSeeder", havingValue = "true")
public class DataSeeder implements CommandLineRunner {

    private final ShowRepository showRepository;

    @Autowired
    public DataSeeder(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        loadShowsData();
    }

    private void loadShowsData() {
        if (this.showRepository.count() == 0) {
            List<Show> shows = Arrays.asList(
                new Show("Dancing Queen"),
                new Show("Beauty and the beast"),
                new Show("West Side Story"),
                    new Show("Aladdin")
            );
            showRepository.saveAll(shows);
        }
    }
}
