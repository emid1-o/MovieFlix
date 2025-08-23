package br.com.movieflix.service;

import br.com.movieflix.entity.Category;
import br.com.movieflix.entity.Movie;
import br.com.movieflix.entity.Streaming;
import br.com.movieflix.repository.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class MovieService {

    @Autowired
    private MovieRepository movieRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StreamingService streamingService;

    public Movie save(Movie movie){
        movie.setStreamings(this.findStreamings(movie.getStreamings()));
        movie.setCategories(this.findCategories(movie.getCategories()));
        return movieRepository.save(movie);
    }

    public List<Movie> findAll(){
        return movieRepository.findAll();
    }

    public Optional<Movie> findById(Long id){
        return movieRepository.findById(id);
    }

    private List<Category> findCategories(List<Category> categories) {
        List<Category> categoriesFound = new ArrayList<>();

        categories.forEach(category -> {
            categoryService.findById(category.getId()).ifPresent(categoriesFound :: add);
        });

        return categoriesFound;
    }

    private List<Streaming> findStreamings(List<Streaming> streamings){

        List<Streaming> streamingsFound = new ArrayList<>();

        streamings.forEach(streaming -> streamingService.findById(streaming.getId()).ifPresent(streamingsFound::add));
        return streamingsFound;
    }
}
