package com.kry.livi.utils;

import com.kry.livi.domain.Resource;
import com.kry.livi.dto.ResourceDTO;

import java.time.LocalDateTime;

public class ResourceUtils {
    public static final String RESOURCE_NAME = "google";
    public static final String RESOURCE_URL = "https://google.com";
    public static final String STATUS_UNKNOWN = "UNKNOWN";

    public static Resource getResource(LocalDateTime currentTime) {
        return Resource.builder()
                .name(RESOURCE_NAME)
                .url(RESOURCE_URL)
                .status(STATUS_UNKNOWN)
                .creationTime(currentTime)
                .build();
    }

    public static ResourceDTO getResourceDTO() {
        return ResourceDTO.builder()
                .name(RESOURCE_NAME)
                .url(RESOURCE_URL)
                .status(STATUS_UNKNOWN)
                .build();
    }
}
