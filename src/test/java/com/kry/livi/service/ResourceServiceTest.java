package com.kry.livi.service;

import com.kry.livi.domain.Resource;
import com.kry.livi.dto.ResourceDTO;
import com.kry.livi.exceptions.UrlValidationException;
import com.kry.livi.mapper.ResourceMapper;
import com.kry.livi.repository.ResourceRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.kry.livi.utils.ResourceUtils.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ResourceServiceTest {
    @Mock
    private ResourceRepository repository;
    @Mock
    private ResourceMapper resourceMapper;
    private ResourceService resourceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        resourceService = new ResourceService(repository, resourceMapper);
    }

    @Test
    public void createResource() {
        //given
        ResourceDTO resourceDTO = getResourceDTO();
        LocalDateTime now = LocalDateTime.now();
        Resource resource = getResource(now);
        when(resourceMapper.dtoToDomain(resourceDTO)).thenReturn(resource);
        when(repository.save(resource)).thenReturn(resource);

        //when
        Resource result = resourceService.createResource(resourceDTO);

        //then
        assertEquals(STATUS_UNKNOWN, result.getStatus());
        assertEquals(RESOURCE_URL, result.getUrl());
        assertEquals(RESOURCE_NAME, result.getName());
        verify(repository).save(result);
        verify(resourceMapper).dtoToDomain(resourceDTO);
    }


    @Test
    public void createResource_Exception() {
        //given
        ResourceDTO resourceDTO = getResourceDTO();
        LocalDateTime now = LocalDateTime.now();
        Resource resource = getResource(now);
        resource.setUrl("invalid url");
        when(resourceMapper.dtoToDomain(resourceDTO)).thenReturn(resource);

        //when
        UrlValidationException exception = assertThrows(UrlValidationException.class,
                () -> resourceService.createResource(resourceDTO));

        //then
        assertEquals("The URL must be valid", exception.getMessage());
        verify(resourceMapper).dtoToDomain(resourceDTO);
        verifyNoInteractions(repository);
    }


    @Test
    public void deleteResource() {
        //when
        resourceService.removeResource(3);

        //then
        verify(repository).deleteById(3);
    }

    @Test
    public void deleteResource_Exception() {
        //when
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class,
                () -> resourceService.removeResource(-3));

        //then
        verifyNoInteractions(repository);
        assertEquals("The id must be higher than 0", exception.getMessage());
    }

    @Test
    public void updateResource() {
        //given
        ResourceDTO resourceDTO = getResourceDTO();
        LocalDateTime now = LocalDateTime.now();
        Resource resource = getResource(now);
        when(resourceMapper.dtoToDomain(resourceDTO)).thenReturn(resource);
        when(repository.save(resource)).thenReturn(resource);

        //when
        Resource result = resourceService.updateResource(resourceDTO);

        //then
        assertEquals(STATUS_UNKNOWN, result.getStatus());
        assertEquals(RESOURCE_URL, result.getUrl());
        assertEquals(RESOURCE_NAME, result.getName());
        verify(repository).save(result);
        verify(resourceMapper).dtoToDomain(resourceDTO);
    }

    @Test
    public void findAllResources() {
        //given
        Resource resource = getResource(LocalDateTime.now());
        List<Resource> resources = Collections.singletonList(resource);
        when(repository.findAll()).thenReturn(resources);

        //when
        List<Resource> allResources = resourceService.findAllResources();

        //then
        assertNotNull(allResources);
        assertEquals(1, allResources.size());
        verify(repository).findAll();
    }
}