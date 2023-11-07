package dev.bbzblit.m450.repository;

import dev.bbzblit.m450.model.SchoolClass;
import dev.bbzblit.m450.model.SchoolRoom;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoomRepository extends CrudRepository<SchoolRoom, Long> {

    Optional<SchoolRoom> findByName(String name);

}
