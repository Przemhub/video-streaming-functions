package com.example.videostreamfunction.functions;

import com.example.videostreamfunction.entity.Video;
import com.example.videostreamfunction.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;

import java.util.List;
import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class FilmFunction {

    private final VideoRepository videoRepository;

    @Bean
    public Function<Message<String>, ResponseEntity<List<Video>>> getFilmList() {
        return request -> ResponseEntity.ok()
                .body(videoRepository.findAll());
    }

}
