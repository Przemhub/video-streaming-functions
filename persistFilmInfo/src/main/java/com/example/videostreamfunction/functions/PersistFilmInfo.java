package com.example.videostreamfunction.functions;

import com.example.videostreamfunction.dto.GCSEvent;
import com.example.videostreamfunction.entity.Video;
import com.example.videostreamfunction.repository.VideoRepository;
import com.google.cloud.functions.BackgroundFunction;
import com.google.cloud.functions.Context;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;

import java.util.function.Consumer;

@Configuration
@RequiredArgsConstructor
public class PersistFilmInfo implements Consumer<GCSEvent> {
    private final VideoRepository videoRepository;
    private static final Logger logger = LoggerFactory.getLogger(PersistFilmInfo.class);

    @Override
    public void accept(GCSEvent event)
    {
        logger.error(event.getName());
        logger.error(event.getMetadata().toString());
        String description = event.getMetadata().get("description");
        logger.error(event.getMetadata().get("description"));
        Video savedVideo = saveVideoEntity(event.getName(),description);
    };


    private Video saveVideoEntity(String videoName, String description) {
        return videoRepository.save(
                Video.builder()
                        .videoName(videoName)
                        .description(description)
                        .build()
        );
    }


    @Override
    public Consumer<GCSEvent> andThen(Consumer<? super GCSEvent> after) {
        return Consumer.super.andThen(after);
    }
}

