package com.example.videostreamfunction.functions;

import com.example.videostreamfunction.dto.GCSEvent;
import com.example.videostreamfunction.entity.Video;
import com.example.videostreamfunction.repository.VideoRepository;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class PersistFilmInfo implements Consumer<GCSEvent> {
    private static final Logger logger = LoggerFactory.getLogger(PersistFilmInfo.class);
    private final VideoRepository videoRepository;

    @Override
    public void accept(GCSEvent event) {
        String description = event.getMetadata().get("description");
        saveVideoEntity(event.getName(), description);
    }

    private void saveVideoEntity(String videoName, String description) {
        videoRepository.save(
                Video.builder()
                        .videoName(videoName)
                        .description(description)
                        .build()
        );
    }
}

