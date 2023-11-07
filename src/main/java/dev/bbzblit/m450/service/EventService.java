package dev.bbzblit.m450.service;

import dev.bbzblit.m450.model.SchoolEvent;
import dev.bbzblit.m450.repository.EventRepository;
import org.springframework.stereotype.Service;

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
                () -> new RuntimeException("Event with id " + id + " not found")
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
}
