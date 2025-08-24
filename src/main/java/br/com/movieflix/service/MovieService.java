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

    public Optional<Movie> update(Movie movie, Long id) {

        Optional<Movie> optMovie = movieRepository.findById(id);

        List<Category> categories = this.findCategories(movie.getCategories());
        List<Streaming> streamings = this.findStreamings(movie.getStreamings());

        if (optMovie.isPresent()){
            Movie filme = optMovie.get();
            filme.setTitle(movie.getTitle());
            filme.setRating(movie.getRating());
            filme.setDescription(movie.getDescription());
            filme.setReleaseDate(movie.getReleaseDate());
            filme.getCategories().clear();
            filme.setCategories(categories);
            filme.getStreamings().clear();
            filme.setStreamings(streamings);

            Movie movieUpdated = movieRepository.save(filme);

            return Optional.of(movieUpdated);
        }
        return Optional.empty();


    }

    public void delete(Long id){
        movieRepository.deleteById(id);
    }

    public Optional<List<Movie>> findByCategory(Long categoryId){

        Optional<List<Movie>> lista = movieRepository.findByCategoryId(categoryId);

        if(lista.isPresent()){
            return lista;
        }
        return Optional.empty();
    }
}
