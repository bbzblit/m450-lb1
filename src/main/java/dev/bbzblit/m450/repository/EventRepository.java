package dev.bbzblit.m450.repository;

import dev.bbzblit.m450.model.SchoolEvent;
import jdk.jfr.Event;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EventRepository extends CrudRepository<SchoolEvent, Long> {

    Iterable<SchoolEvent> findAllByClassIdId(Long classId);

}
