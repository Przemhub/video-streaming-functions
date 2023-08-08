package com.example.videostreamfunction.entity;


import jakarta.persistence.*;
import lombok.*;

//import org.springframework.data.relational.core.mapping.Table;


import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Video {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String videoName;
    @Column( length = 100000 )
    private String description;
}
