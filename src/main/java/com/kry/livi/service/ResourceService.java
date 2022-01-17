package com.kry.livi.service;

import com.kry.livi.domain.Resource;
import com.kry.livi.domain.Status;
import com.kry.livi.exceptions.UrlValidationException;
import com.kry.livi.dto.ResourceDTO;
import com.kry.livi.mapper.ResourceMapper;
import com.kry.livi.repository.ResourceRepository;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.UrlValidator;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ResourceService {
    private final ResourceRepository repository;
    private final ResourceMapper mapper;

    public Resource createResource(ResourceDTO resourceDTO) {
        Resource resource = mapper.dtoToDomain(resourceDTO);
        validateUrl(resource.getUrl());
        resource.setStatus(Status.UNKNOWN.name());
        resource.setCreationTime(LocalDateTime.now());
        return repository.save(resource);
    }

    public void removeResource(int id) {
        if (id < 0) {
            throw new IllegalArgumentException("The id must be higher than 0");
        }
        repository.deleteById(id);
    }

    public Resource updateResource(ResourceDTO resourceDTO) {
        Resource resource = mapper.dtoToDomain(resourceDTO);
        validateUrl(resource.getUrl());
        resource.setStatus(Status.UNKNOWN.name());
        resource.setCreationTime(LocalDateTime.now());
        return repository.save(resource);
    }

    public List<Resource> findAllResources() {
        return repository.findAll();
    }

    private void validateUrl(String url) {
        String[] schemes = {"http", "https", "ftp"};
        UrlValidator urlValidator = new UrlValidator(schemes);
        if (!urlValidator.isValid(url)) {
            throw new UrlValidationException("The URL must be valid");
        }
    }
}
