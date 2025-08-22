package br.com.movieflix.service;

import br.com.movieflix.entity.Streaming;
import br.com.movieflix.repository.StreamingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public Optional<Streaming> findById(Long id){
        return streamingRepository.findById(id);
    }

    public void deleteById(Long id){
        if (streamingRepository.existsById(id)){
            streamingRepository.deleteById(id);
        }

    }
}
