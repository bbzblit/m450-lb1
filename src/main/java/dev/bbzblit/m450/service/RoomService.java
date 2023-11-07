package dev.bbzblit.m450.service;

import dev.bbzblit.m450.model.SchoolRoom;
import dev.bbzblit.m450.repository.RoomRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class RoomService implements ParentService<SchoolRoom, Long>{

    private final RoomRepository roomRepository;

    public RoomService(final RoomRepository roomRepository){
        this.roomRepository = roomRepository;
    }

    public SchoolRoom save(SchoolRoom entity){
        return this.roomRepository.save(entity);
    }

    public SchoolRoom findById(Long id){
        return this.roomRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND, "No room with id " + id + " found."
                )
        );
    }

    public Iterable<SchoolRoom> findAll(){
        return this.roomRepository.findAll();
    }

    public void deleteById(Long id){
        this.findById(id);
        this.roomRepository.deleteById(id);
    }

}
