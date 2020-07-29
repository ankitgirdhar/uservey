package com.spring.uservey.repositories;

import com.spring.uservey.models.Survey;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SurveyRepository extends CrudRepository<Survey, Long> {

    Survey findByUsername(String username);
    Survey getById(Long id);
    Iterable<Survey> findAllByUsername(String name);
}
