package com.kry.livi.mapper;

import com.kry.livi.domain.Resource;
import com.kry.livi.dto.ResourceDTO;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ResourceMapper {

    public ResourceDTO domainToDTO(Resource resource) {
        return ResourceDTO
                .builder()
                .name(resource.getName())
                .url(resource.getUrl())
                .creationDate(resource.getCreationTime())
                .status(resource.getStatus())
                .build();
    }

    public Resource dtoToDomain(ResourceDTO dto) {
        return Resource.builder()
                .name(dto.getName())
                .url(dto.getUrl())
                .build();
    }

    public List<ResourceDTO> domainsToDTOs(List<Resource> resources) {
        return resources.stream().map(this::domainToDTO).collect(Collectors.toList());
    }
}
