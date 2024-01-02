package com.rune.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TEDHelperRepository extends CrudRepository<TEDHelperRepoBean, Integer> {

}
