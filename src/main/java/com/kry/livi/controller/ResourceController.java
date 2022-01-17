package com.kry.livi.controller;

import com.kry.livi.dto.ResourceDTO;
import com.kry.livi.mapper.ResourceMapper;
import com.kry.livi.service.ResourceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping(value = "/resource")
@RequiredArgsConstructor
public class ResourceController {
    private final ResourceMapper resourceMapper;
    private final ResourceService resourceService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResourceDTO createResource(@RequestBody @Valid ResourceDTO resourceDTO) {
        return resourceMapper.domainToDTO(resourceService.createResource(resourceDTO));
    }

    @DeleteMapping("/{resourceId}")
    public void deleteResource(@PathVariable(value = "resourceId") int id) {
        resourceService.removeResource(id);
    }

    @PutMapping
    public ResourceDTO updateResource(@RequestBody @Valid ResourceDTO resourceDTO) {
        return resourceMapper.domainToDTO(resourceService.updateResource(resourceDTO));
    }

    @GetMapping
    public List<ResourceDTO> getAllResources() {
        return resourceMapper.domainsToDTOs(resourceService.findAllResources());
    }
}
