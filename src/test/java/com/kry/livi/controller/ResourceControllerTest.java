package com.kry.livi.controller;

import com.kry.livi.domain.Resource;
import com.kry.livi.dto.ResourceDTO;
import com.kry.livi.mapper.ResourceMapper;
import com.kry.livi.service.ResourceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;

import static com.kry.livi.utils.ResourceUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;


class ResourceControllerTest {
    private ResourceController resourceController;
    @Mock
    private ResourceMapper resourceMapper;
    @Mock
    private ResourceService resourceService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        resourceController = new ResourceController(resourceMapper, resourceService);
    }

    @Test
    public void createResource() {
        //given
        ResourceDTO resourceDTO = getResourceDTO();
        LocalDateTime currentTime = LocalDateTime.now();
        Resource resource = getResource(currentTime);
        when(resourceMapper.domainToDTO(resource)).thenReturn(resourceDTO);
        when(resourceService.createResource(resourceDTO)).thenReturn(resource);

        //when
        ResourceDTO response = resourceController.createResource(resourceDTO);

        //then
        assertEquals(STATUS_UNKNOWN, response.getStatus());
        assertEquals(RESOURCE_NAME, response.getName());
        assertEquals(currentTime, resource.getCreationTime());
        verify(resourceMapper).domainToDTO(resource);
        verify(resourceService).createResource(resourceDTO);
    }

    @Test
    public void updateResource() {
        //given
        ResourceDTO resourceDTO = getResourceDTO();
        LocalDateTime currentTime = LocalDateTime.now();
        Resource resource = getResource(currentTime);
        when(resourceMapper.domainToDTO(resource)).thenReturn(resourceDTO);
        when(resourceService.updateResource(resourceDTO)).thenReturn(resource);

        //when
        ResourceDTO response = resourceController.updateResource(resourceDTO);

        //then
        assertEquals(STATUS_UNKNOWN, response.getStatus());
        assertEquals(RESOURCE_NAME, response.getName());
        assertEquals(currentTime, resource.getCreationTime());
        verify(resourceMapper).domainToDTO(resource);
        verify(resourceService).updateResource(resourceDTO);
    }

    @Test
    public void deleteResource() {
        //given
        doNothing().when(resourceService).removeResource(3);

        //when
        resourceController.deleteResource(3);

        //then
        verify(resourceService).removeResource(3);

    }

    @Test
    public void getAllResources() {
        //when
        Resource resource = getResource(LocalDateTime.now());
        ResourceDTO resourceDTO = getResourceDTO();
        List<Resource> resources = Collections.singletonList(resource);
        when(resourceService.findAllResources()).thenReturn(resources);
        when(resourceMapper.domainsToDTOs(resources)).thenReturn(Collections.singletonList(resourceDTO));

        //when
        List<ResourceDTO> allResources = resourceController.getAllResources();

        //then
        assertNotNull(allResources);
        assertEquals(1, allResources.size());
        verify(resourceService).findAllResources();
        verify(resourceMapper).domainsToDTOs(resources);
    }
}