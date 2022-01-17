package com.kry.livi.repository;

import com.kry.livi.domain.Resource;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ResourceRepository extends CrudRepository<Resource, Integer> {
    @Override
    Resource save(Resource resource);

    @Override
    Optional<Resource> findById(Integer id);

    void deleteById(int id);

    @Override
    List<Resource> findAll();
}
