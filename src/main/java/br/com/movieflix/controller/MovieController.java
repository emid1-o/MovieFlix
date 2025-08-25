package br.com.movieflix.controller;

import br.com.movieflix.controller.request.MovieRequest;
import br.com.movieflix.controller.response.MovieResponse;
import br.com.movieflix.entity.Movie;
import br.com.movieflix.mapper.MovieMapper;
import br.com.movieflix.service.MovieService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/movieflix/movie")
public class MovieController {

    @Autowired
    private MovieService movieService;

    @PostMapping
    public ResponseEntity<MovieResponse> save(@RequestBody MovieRequest request){
        Movie savedMovie = movieService.save(MovieMapper.toMovie(request));
        MovieResponse response = MovieMapper.toMovieResponse(savedMovie);
        return ResponseEntity.status(HttpStatus.CREATED).body(MovieMapper.toMovieResponse(savedMovie));
    }

    @GetMapping
    public ResponseEntity<List<MovieResponse>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(movieService.findAll().stream()
                .map(movie -> MovieMapper.toMovieResponse(movie)).toList()) ;
    }

    @GetMapping("/{id}")
    public ResponseEntity<MovieResponse> findById(@PathVariable Long id){
        return movieService.findById(id)
                .map(movie -> ResponseEntity.ok(MovieMapper.toMovieResponse(movie)))
                .orElse(ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<MovieResponse> update(@RequestBody MovieRequest request,@PathVariable Long id){
        Movie movie = MovieMapper.toMovie(request);

        return movieService.update(movie, id).map(movie1 ->
            ResponseEntity.ok(MovieMapper.toMovieResponse(movie1))
        ).orElse(ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        if (movieService.findById(id).isPresent()){
            movieService.delete(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
    }

    @GetMapping("/category/{id}")
    public ResponseEntity<List<MovieResponse>> findByCategory(@PathVariable Long id){

        List<Movie> lista = movieService.findByCategory(id);
        List<MovieResponse> resposta = new ArrayList<>();
        if (!lista.isEmpty()){
            for (Movie movie : lista) {
                resposta.add(MovieMapper.toMovieResponse(movie));
            }
            return ResponseEntity.ok(resposta);
        }

        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
