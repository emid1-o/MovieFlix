package br.com.movieflix.service;

import br.com.movieflix.entity.Streaming;
import br.com.movieflix.repository.StreamingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@Service
public class StreamingService {

    @Autowired
    private StreamingRepository streamingRepository;

    public List<Streaming> findAll(){
        return streamingRepository.findAll();
    }

    public Streaming save(Streaming streaming){
        return streamingRepository.save(streaming);
    }

    public Streaming update(Streaming streaming, Long id){

        return streamingRepository.findById(id).map(streaming1 ->{
            streaming1.setName(streaming.getName());

            return streamingRepository.save(streaming1); }).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.NOT_FOUND ,"streaming de id " + id + " não encontrado")
        );
    }

    public Optional<Streaming> findById(Long id){
        return streamingRepository.findById(id);
    }

    public void deleteById(Long id){
        if (streamingRepository.existsById(id)){
            streamingRepository.deleteById(id);
        }

    }
}
