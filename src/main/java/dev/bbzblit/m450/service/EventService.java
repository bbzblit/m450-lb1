package dev.bbzblit.m450.service;

import dev.bbzblit.m450.model.SchoolEvent;
import dev.bbzblit.m450.repository.EventRepository;

import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class EventService implements ParentService<SchoolEvent, Long>{

    private final EventRepository eventRepository;

    public EventService(EventRepository eventRepository) {
        this.eventRepository = eventRepository;
    }

    @Override
    public SchoolEvent save(SchoolEvent entity) {
        return this.eventRepository.save(entity);
    }

    @Override
    public SchoolEvent findById(Long id) {
        return this.eventRepository.findById(id).orElseThrow(
                () -> new ResponseStatusException(HttpStatusCode.valueOf(404), "No event with id " + id + " found.")
        );
    }

    @Override
    public Iterable<SchoolEvent> findAll() {
        return this.eventRepository.findAll();
    }

    @Override
    public void deleteById(Long id) {
        this.findById(id);
        this.eventRepository.deleteById(id);
    }

    public Iterable<SchoolEvent> getAllEventsOfClass(Long classId) {
        return this.eventRepository.findAllByClassIdId(classId);
    }
}
