package br.com.movieflix.controller;

import br.com.movieflix.controller.request.StreamingRequest;
import br.com.movieflix.controller.response.StreamingResponse;
import br.com.movieflix.entity.Streaming;
import br.com.movieflix.mapper.StreamingMapper;
import br.com.movieflix.service.StreamingService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/movieflix/streaming")
public class StreamingController {

    @Autowired
    private StreamingService streamingService;

    @GetMapping
    public ResponseEntity<List<StreamingResponse>> getAllStreamings(){
        List<Streaming> streamings = streamingService.findAll();
        List<StreamingResponse> list = streamings.stream()
                .map(streaming -> StreamingMapper.toStreamingResponse(streaming))
                .toList();

        return ResponseEntity.ok(list);
    }

    @PostMapping
    public ResponseEntity<StreamingResponse>  saveStreaming(@Valid @RequestBody StreamingRequest request){
        Streaming newStreaming = StreamingMapper.toStreaming(request);
        Streaming savedStreaming = streamingService.save(newStreaming);
        StreamingResponse streamingResponse = StreamingMapper.toStreamingResponse(savedStreaming);

        return ResponseEntity.status(HttpStatus.CREATED).body(streamingResponse);

    }

    @PutMapping("{id}")
    public ResponseEntity<StreamingResponse> update(@Valid @RequestBody StreamingRequest request, @PathVariable Long id){
        Streaming newStreaming = StreamingMapper.toStreaming(request);
        Streaming updatedStreaming = streamingService.update(newStreaming, id);
        return ResponseEntity.status(HttpStatus.CREATED).body(StreamingMapper.toStreamingResponse(updatedStreaming));
    }

    @GetMapping("/{id}")
    public ResponseEntity<StreamingResponse>  findById(@PathVariable Long id){
        return streamingService.findById(id)
                .map(streaming -> ResponseEntity.ok(StreamingMapper.toStreamingResponse(streaming)))
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id){
        streamingService.deleteById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
