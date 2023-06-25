package com.example.videostreamfunction.functions;

import com.example.videostreamfunction.dto.VideoPostDto;
import com.example.videostreamfunction.entity.Video;
import com.example.videostreamfunction.repository.VideoRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.cloud.functions.HttpRequest;
import com.google.cloud.storage.Blob;
import com.google.cloud.storage.Bucket;
import com.google.cloud.storage.Storage;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.codec.multipart.FilePart;
import org.springframework.http.codec.multipart.FormFieldPart;
import org.springframework.messaging.Message;
import org.springframework.util.ObjectUtils;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@Configuration
@RequiredArgsConstructor
public class FilmFunctions {

    private final VideoRepository videoRepository;
    private static Logger logger = LoggerFactory.getLogger(FilmFunctions.class);

    @Bean
    public Function<VideoPostDto, ResponseEntity<Video>> postFilm() {
        return videoPostDto ->
        {
            Video savedVideo = saveVideoEntity(videoPostDto);
            return ResponseEntity.created(URI.create("/" + savedVideo.getVideoName())).body(savedVideo);
        };

    }

    private Video saveVideoEntity(VideoPostDto videoPostDto) {
        return videoRepository.save(
                Video.builder()
                        .videoName(videoPostDto.getVideoName())
                        .description(videoPostDto.getDescription())
                        .build()
        );
    }

}
