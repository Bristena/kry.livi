package com.kry.livi.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResourceDTO {
    private String url;
    private String name;
    private LocalDateTime creationDate;
    private String status;
}
