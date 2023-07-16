package com.example.videostreamfunction.dto;

import lombok.*;

import java.util.Map;

@Data
@Builder
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class GCSEvent {
    private String name;
    private String bucket;
    private String id;
    private Map<String,String> metadata;
}
