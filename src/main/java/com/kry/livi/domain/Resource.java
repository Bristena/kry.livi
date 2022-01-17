package com.kry.livi.domain;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
public class Resource {
    private String name;
    private String url;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String status;
    @Column(name = "creation_time", updatable = false)
    private LocalDateTime creationTime;
}
