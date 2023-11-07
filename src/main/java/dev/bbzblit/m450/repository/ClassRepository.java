package dev.bbzblit.m450.repository;

import dev.bbzblit.m450.model.SchoolClass;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ClassRepository extends CrudRepository<SchoolClass, Long> {
}
